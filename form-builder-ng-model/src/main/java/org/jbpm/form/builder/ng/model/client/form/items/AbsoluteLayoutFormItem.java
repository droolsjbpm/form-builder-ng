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
import org.jbpm.form.builder.ng.model.client.effect.FBFormEffect;
import org.jbpm.form.builder.ng.model.client.form.FBFormItem;
import org.jbpm.form.builder.ng.model.client.form.LayoutFormItem;
import org.jbpm.form.builder.ng.model.client.form.PhantomPanel;
import org.jbpm.form.builder.ng.model.client.messages.I18NConstants;
import org.jbpm.form.builder.ng.model.shared.api.FormBuilderDTO;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.Reflectable;

/**
 * UI form layout item. Represents an absolute layout
 */
@Reflectable
public class AbsoluteLayoutFormItem extends LayoutFormItem {

    private final I18NConstants i18n = CommonGlobals.getInstance().getI18n();
    
    private AbsolutePanel panel = new AbsolutePanel() {
        @Override
        public boolean remove(Widget widget) {
            if (widget instanceof FBFormItem) {
                AbsoluteLayoutFormItem.this.remove(widget);
            }
            return super.remove(widget);
        }
    };
    
    private String id;

    public AbsoluteLayoutFormItem() {
        this(new ArrayList<FBFormEffect>());
    }
    
    public AbsoluteLayoutFormItem(List<FBFormEffect> formEffects) {
        super(formEffects);
        add(panel);
        setSize("90px", "90px");
        panel.setSize(getWidth(), getHeight());
    }

    @Override
    public Map<String, Object> getFormItemPropertiesMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", this.id);
        map.put("height", getHeight());
        map.put("width", getWidth());
        return map;
    }
    
    @Override
    public void saveValues(Map<String, Object> asPropertiesMap) {
        this.id = extractString(asPropertiesMap.get("id"));
        this.setHeight(extractString(asPropertiesMap.get("height")));
        this.setWidth(extractString(asPropertiesMap.get("width")));
        populate(this.panel);
    }

    private void populate(AbsolutePanel panel) {
        if (this.getHeight() != null) {
            panel.setHeight(this.getHeight());
        } 
        if (this.getWidth() != null) {
            panel.setWidth(this.getWidth());
        }
    }

    @Override
    public FormBuilderDTO getRepresentation() {
    	FormBuilderDTO dto = super.getRepresentation();
        dto.setString("id", this.id);
        List<Object> items = new ArrayList<Object>();
        for (FBFormItem item : getItems()) {
        	FormBuilderDTO subDto = item.getRepresentation();
        	int x = item.getDesiredX() - panel.getAbsoluteLeft();
        	int y = item.getDesiredY() - panel.getAbsoluteTop();
        	subDto.setInteger("x", x);
        	subDto.setInteger("y", y);
        	items.add(subDto.getParameters());
        }
        dto.setList("items", items);
        return dto;
    }
    
    @Override
    public void populate(FormBuilderDTO dto) throws FormBuilderException {
        if (!dto.getClassName().endsWith("AbsolutePanelRepresentation")) {
            throw new FormBuilderException(i18n.RepNotOfType(dto.getClassName(), "AbsolutePanelRepresentation"));
        }
        super.populate(dto);
        panel.clear();
        getItems().clear();
        List<FormBuilderDTO> items = dto.getListOfDtos("items");
        if (items != null) {
        	for (FormBuilderDTO itemDto : items) {
        		FBFormItem item = super.createItem(itemDto);
        		item.setDesiredPosition(itemDto.getInteger("x"), itemDto.getInteger("y"));
        		this.add(item);
        	}
        }
        populate(this.panel);
    }

    @Override
    public FBFormItem cloneItem() {
        AbsoluteLayoutFormItem clone = new AbsoluteLayoutFormItem(getFormEffects());
        clone.setHeight(this.getHeight());
        clone.id = this.id;
        clone.setWidth(this.getWidth());
        clone.populate(clone.panel);
        for (FBFormItem item : getItems()) {
            clone.add(item.cloneItem());
        }
        return clone;
    }

    @Override
    public boolean add(FBFormItem item) {
        int left = item.getDesiredX();
        int top = item.getDesiredY();
        panel.add(item, left - panel.getAbsoluteLeft(), top - panel.getAbsoluteTop());
        return super.add(item);
    }
    
    @Override
    public boolean remove(Widget child) {
        return super.remove(child);
    }
    
    @Override
    public HasWidgets getPanel() {
        return this.panel;
    }

    @Override
    public Widget cloneDisplay(Map<String, Object> data) {
        AbsolutePanel ap = new AbsolutePanel();
        populate(ap);
        for (FBFormItem item : getItems()) {
            ap.add(item.cloneDisplay(data), this.getAbsoluteLeft() - item.getDesiredX(), this.getAbsoluteTop() - item.getDesiredY());
        }
        super.populateActions(ap.getElement());
        return ap;
    }

    private String oldStyleName = null;
    
    @Override
    public void add(PhantomPanel phantom, int x, int y) {
        String styleName = phantom.getStyleName();
        oldStyleName = getStyleName();
        setStyleName(styleName);
    }

    @Override
    public void replacePhantom(FBFormItem item) {
        if (oldStyleName == null) {
            removeStyleName(getStyleName());
        } else {
            setStyleName(oldStyleName);
        }
        oldStyleName = null;
        add(item);
    }
}
