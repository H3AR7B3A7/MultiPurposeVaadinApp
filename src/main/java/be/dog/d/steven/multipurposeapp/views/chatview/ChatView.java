package be.dog.d.steven.multipurposeapp.views.chatview;

import be.dog.d.steven.multipurposeapp.model.ChatMessage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import be.dog.d.steven.multipurposeapp.views.main.MainView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Route(value = "chat", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Chat")
@CssImport("styles/views/chatview/chat-view.css")
public class ChatView extends VerticalLayout {

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;
    private String userName;

    public ChatView(UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages){
        this.publisher = publisher;
        this.messages = messages;

        setSizeFull();
        addClassName("chat-view");

        H1 header = new H1("Vaadin Chat");

        add(header);

        askUserName();
    }

    private void askUserName(){
        HorizontalLayout userNameLayout = new HorizontalLayout();

        TextField userNameField = new TextField();
        Button startButton = new Button("Enter chat");
        userNameLayout.add(userNameField, startButton);

        startButton.addClickListener(click -> {
            userName = userNameField.getValue();
            remove(userNameLayout);
            showChat();
        });

        add(userNameLayout);
    }

    private void showChat(){
        MessageList messageList = new MessageList();

        add(messageList, createInputLayout());
        expand(messageList);

        messages.subscribe(message -> getUI().ifPresent(ui->
                ui.access(()->
                        messageList.add(new Paragraph(
                                message.getFrom() + ": " + message.getMessage()
                        ))
                )));
    }

    private Component createInputLayout() {
        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.setWidth("100%");

        TextField messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.getElement().getThemeList().add("primary");

        inputLayout.add(messageField, sendButton);
        inputLayout.expand(messageField);

        sendButton.addClickListener(click->{
            publisher.onNext(new ChatMessage(userName, messageField.getValue()));
            messageField.clear();
            messageField.focus();
        });
        messageField.focus();

        return inputLayout;
    }

}
