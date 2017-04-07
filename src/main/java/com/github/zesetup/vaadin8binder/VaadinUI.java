package com.github.zesetup.vaadin8binder;

import com.vaadin.annotations.Theme;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.BindingValidationStatusHandler;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;



@SpringUI
@Theme("valo")
public class VaadinUI extends UI {
  public VaadinUI() {  
  }

  @Override
  protected void init(VaadinRequest request) {
    BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    binder.forField(name).withValidationStatusHandler(new BindingValidationStatusHandler() {
      private static final long serialVersionUID = 1L;
      @Override
      public void statusChange(BindingValidationStatus<?> statusChange) {
        if (statusChange.getMessage().isPresent()) {
          name.setComponentError(new UserError(statusChange.getMessage().get()));
        } else {
          name.setComponentError(null);
        }
      }
    })
    .bind("name");
    VerticalLayout formContent = new VerticalLayout();
    formContent.addComponents(name, surname);
    setContent(formContent);
  }
}
