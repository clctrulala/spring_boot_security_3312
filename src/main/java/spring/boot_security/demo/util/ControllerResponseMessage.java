package spring.boot_security.demo.util;

import org.springframework.ui.ModelMap;

public class ControllerResponseMessage {

    private String positiveMessage;

    private String negativeMessage;

    private ModelMap model;

    public ControllerResponseMessage setModel(ModelMap model) {
        this.model = model;
        return this;
    }

    public ControllerResponseMessage setMessages(String positiveMessage, String negativeMessage) {
        this.positiveMessage = positiveMessage;
        this.negativeMessage = negativeMessage;
        return this;
    }

    public ModelMap selectMessage(boolean selector) {
        return model.addAttribute("response", selector ? positiveMessage : negativeMessage);
    }
}
