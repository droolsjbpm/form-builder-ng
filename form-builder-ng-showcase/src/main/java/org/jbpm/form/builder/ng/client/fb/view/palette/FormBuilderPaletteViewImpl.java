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
package org.jbpm.form.builder.ng.client.fb.view.palette;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jbpm.form.builder.ng.model.client.bus.FormItemSelectionEvent;
import org.jbpm.form.builder.ng.model.client.bus.FormItemSelectionHandler;
import org.jbpm.form.builder.ng.model.client.menu.FBMenuItem;
import org.jbpm.form.builder.ng.model.common.reflect.ReflectionHelper;
import org.jbpm.form.builder.ng.model.shared.api.FormBuilderDTO;
import org.jbpm.form.builder.ng.model.shared.menu.items.CustomMenuItem;
import org.jbpm.form.builder.ng.shared.events.PaletteItemAddedEvent;
import org.uberfire.client.mvp.PlaceManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Main view. Uses UIBinder to define the correct position of components
 */
@Dependent
public class FormBuilderPaletteViewImpl extends AbsolutePanel
        implements
        FormBuilderPalettePresenter.FormBuilderView {

    interface FormBuilderViewImplBinder
            extends
            UiBinder<Widget, FormBuilderPaletteViewImpl> {
    }
    private static FormBuilderViewImplBinder uiBinder = GWT.create(FormBuilderViewImplBinder.class);
    @Inject
    private PlaceManager placeManager;
    private FormBuilderPalettePresenter presenter;
    @Inject
    private Event<FormItemSelectionEvent> eventSelection;
    public @UiField(provided = true)
    ScrollPanel menuView;
    
    @Override
    public void init(final FormBuilderPalettePresenter presenter) {
        this.presenter = presenter;
        init();
    }

    protected final void init() {
        menuView = new AnimatedPaletteViewImpl();
        
        
        menuView.setSize("235px",
                "100%");
        
        add(uiBinder.createAndBindUi(this));

        ((PaletteView) menuView).removeAllItems();

    }

     public void addItem(@Observes PaletteItemAddedEvent event) {
        try {
            String group = event.getGroupName();
            FormBuilderDTO menuItemDescription = new FormBuilderDTO(event.getMenuItemDescriptionMap());
            Object newInstance = ReflectionHelper.newInstance(menuItemDescription.getString("className"));
            if (newInstance instanceof CustomMenuItem) {
                
                   CustomMenuItem customItem = (CustomMenuItem) newInstance;
                   String optionName = menuItemDescription.getString("name");
                   
                   FormBuilderDTO itemDto = new FormBuilderDTO(menuItemDescription.getMap("itemRepresentationMap"));
                   customItem.setRepresentation(itemDto);
                   customItem.setOptionName(optionName);
                   customItem.setGroupName(event.getGroupName());
                   if (menuItemDescription.getString("iconUrl") != null) {
                     String baseUrl = GWT.getHostPageBaseURL().replace(GWT.getModuleName() + "/", "");
                     customItem.setIconUrlAsString(baseUrl + menuItemDescription.getString("iconUrl"));
                   }
                   customItem.repaint();                   
               }
            FBMenuItem item = (FBMenuItem) newInstance;
            item.setItemSelectionHandler(new FormItemSelectionHandler() {
                @Override
            	public void onEvent(FormItemSelectionEvent event) {
            		eventSelection.fire(event);
            	}
            });
            ((PaletteView) menuView).addItem(group,
                    item);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(FormBuilderPaletteViewImpl.class.getName()).log(Level.SEVERE,
                    null,
                    ex);
        }
    }

   
    public ScrollPanel getMenuView() {
        return menuView;
    }

   
    public AbsolutePanel getPanel() {
        return this;
    }

   
}
