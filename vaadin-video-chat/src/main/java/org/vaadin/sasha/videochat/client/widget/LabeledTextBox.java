package org.vaadin.sasha.videochat.client.widget;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class LabeledTextBox extends Composite implements HasValue<String>, HasValueChangeHandlers<String> {

    private final FlowPanel panel = new FlowPanel();
    
    private final TextBox box = new TextBox();
    
    private Label label = new Label();
    
    public LabeledTextBox() {
        initWidget(panel);
        addStyleName("labeled-textfield");
        addStyleName("clearfix");
        construct();
    }

    private void construct() {
        label.addStyleName("label");
        box.addStyleName("field");
        panel.add(label);
        panel.add(box);
    }

    public void setText(String text) {
        label.setText(text);    
    }
    
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return box.addValueChangeHandler(handler);
    }

    @Override
    public String getValue() {
        return box.getValue();
    }

    @Override
    public void setValue(String value) {
        box.setValue(value);
    }

    @Override
    public void setValue(String value, boolean fireEvents) {
        box.setValue(value, fireEvents);
    }
}
