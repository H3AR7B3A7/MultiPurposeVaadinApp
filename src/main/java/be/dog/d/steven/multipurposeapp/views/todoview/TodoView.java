package be.dog.d.steven.multipurposeapp.views.todoview;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import be.dog.d.steven.multipurposeapp.views.main.MainView;

@Route(value = "todo", layout = MainView.class)
@PageTitle("Todo")
@CssImport("styles/views/todoview/todo-view.css")
public class TodoView extends Div {

    public TodoView() {
        setId("todo-view");
        add(new Label("Content placeholder"));
    }

}
