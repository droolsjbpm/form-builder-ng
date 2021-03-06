/*
 * Copyright 2011 JBoss Inc 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.form.builder.ng.model.client.form.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.form.builder.ng.model.client.CommonGlobals;
import org.jbpm.form.builder.ng.model.client.FormBuilderException;
import org.jbpm.form.builder.ng.model.client.bus.ui.NotificationEvent;
import org.jbpm.form.builder.ng.model.client.bus.ui.NotificationEvent.Level;
import org.jbpm.form.builder.ng.model.client.effect.FBFormEffect;
import org.jbpm.form.builder.ng.model.client.form.FBFormItem;
import org.jbpm.form.builder.ng.model.client.form.LayoutFormItem;
import org.jbpm.form.builder.ng.model.client.form.PhantomPanel;
import org.jbpm.form.builder.ng.model.client.messages.I18NConstants;
import org.jbpm.form.builder.ng.model.shared.api.FormBuilderDTO;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.Reflectable;

/**
 * server-side form item. Represents a conditional block (if-else)
 */
@Reflectable
public class ConditionalBlockFormItem extends LayoutFormItem {

    private final I18NConstants i18n = CommonGlobals.getInstance().getI18n();
    private final EventBus bus = CommonGlobals.getInstance().getEventBus();
    
    private Grid display = new Grid(2, 1) {
        @Override
        public boolean remove(Widget w) {
            if (w == ifBlock) {
                ifBlock = null;
            }
            if (w == elseBlock) {
                elseBlock = null;
            }
            return super.remove(w);
        }
    };
    
    private FBFormItem ifBlock = null;
    private FBFormItem elseBlock = null;
    private String conditionScript = "true";

    public ConditionalBlockFormItem() {
        this(new ArrayList<FBFormEffect>());
    }
    
    public ConditionalBlockFormItem(List<FBFormEffect> formEffects) {
        super(formEffects);
        display.setBorderWidth(1);
        display.setStyleName("conditionalBlockBorder");
        display.setSize("100%", "50px");
        add(display);
        setSize("100%", "50px");
    }
    
    @Override
    public Map<String, Object> getFormItemPropertiesMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("conditionScript", this.conditionScript);
        return map;
    }

    @Override
    public void saveValues(Map<String, Object> asPropertiesMap) {
        this.conditionScript = extractString(asPropertiesMap.get("conditionScript"));
    }

    @Override
    public FormBuilderDTO getRepresentation() {
        FormBuilderDTO dto = super.getRepresentation();
        dto.setString("condition", conditionScript);
        dto.setMap("ifBlock", ifBlock == null ? null : ifBlock.getRepresentation().getParameters());
        dto.setMap("elseBlock", elseBlock == null ? null : elseBlock.getRepresentation().getParameters());
        return dto;
    }

    @Override
    public void populate(FormBuilderDTO dto) throws FormBuilderException {
        if (!dto.getClassName().endsWith("ConditionalBlockRepresentation")) {
            throw new FormBuilderException(i18n.RepNotOfType(dto.getClassName(), "ConditionalBlockRepresentation"));
        }
        super.populate(dto);
        this.conditionScript = dto.getString("condition");
        FormBuilderDTO ifDto = new FormBuilderDTO(dto.getMap("ifBlock"));
        if (ifDto.getParameters() == null || ifDto.getParameters().isEmpty()) {
            this.ifBlock = null;
        } else {
            this.ifBlock = createItem(ifDto);
        }
        FormBuilderDTO elseDto = new FormBuilderDTO(dto.getMap("elseBlock"));
        if (elseDto.getParameters() == null || elseDto.getParameters().isEmpty()) {
            this.elseBlock = null;
        } else {
            this.elseBlock = createItem(elseDto);
        }
    }
    
    @Override
    public FBFormItem cloneItem() {
        ConditionalBlockFormItem item = new ConditionalBlockFormItem(super.getFormEffects());
        item.conditionScript = this.conditionScript;
        item.elseBlock = this.elseBlock == null ? null : this.elseBlock.cloneItem();
        item.ifBlock = this.ifBlock == null ? null : this.ifBlock.cloneItem();
        return item;
    }

    @Override
    public Widget cloneDisplay(Map<String, Object> data) {
        Widget elseBlock = this.elseBlock == null ? null : this.elseBlock.cloneDisplay(data);
        Widget ifBlock = this.ifBlock == null ? null : this.ifBlock.cloneDisplay(data);
        String condition = this.conditionScript;
        boolean result = eval0(condition);
        Widget actualBlock = result ? ifBlock : elseBlock;
        if (actualBlock != null) {
            super.populateActions(actualBlock.getElement());
        }
        return actualBlock;
    }
    
    private native boolean eval0(String condition) /*-{
        return eval(condition);
    }-*/;

    @Override
    public HasWidgets getPanel() {
        return display;
    }

    @Override
    public void add(PhantomPanel phantom, int x, int y) {
        Element ifDisplay = display.getCellFormatter().getElement(0, 0);
        if (x > ifDisplay.getAbsoluteLeft() && x < ifDisplay.getAbsoluteRight() 
         && y > ifDisplay.getAbsoluteTop() && y < ifDisplay.getAbsoluteBottom()) {
            display.setWidget(0, 0, phantom);
        }
        Element elseDisplay = display.getCellFormatter().getElement(1, 0);
        if (x > elseDisplay.getAbsoluteLeft() && x < elseDisplay.getAbsoluteRight() 
         && y > elseDisplay.getAbsoluteTop() && y < elseDisplay.getAbsoluteBottom()) {
            display.setWidget(1, 0, phantom);
        }
    }
    
    @Override
    public boolean add(FBFormItem item) {
        boolean retval = false;
        if (ifBlock != null && elseBlock != null) {
            bus.fireEvent(new NotificationEvent(Level.WARN, i18n.ConditionalBlockFull()));
        } else if (ifBlock == null && elseBlock == null) {
            ifBlock = item;
            display.setWidget(0, 0, item);
            retval = true;
        } else if (ifBlock != null && elseBlock == null) {
            elseBlock = item;
            display.setWidget(1, 0, item);
            retval = true;
        } else if (ifBlock == null && elseBlock != null) {
            ifBlock = item;
            display.setWidget(1, 0, item);
            retval = true;
        }
        return retval;
    }
    
    @Override
    public void replacePhantom(FBFormItem item) {
        if (display.getWidget(0, 0) != null && display.getWidget(0, 0) instanceof PhantomPanel) {
            ifBlock = item;
            display.setWidget(0, 0, item);
        } else if (display.getWidget(1, 0) != null && display.getWidget(1, 0) instanceof PhantomPanel) {
            elseBlock = item;
            display.setWidget(1, 0, item);
        }
    }
}
