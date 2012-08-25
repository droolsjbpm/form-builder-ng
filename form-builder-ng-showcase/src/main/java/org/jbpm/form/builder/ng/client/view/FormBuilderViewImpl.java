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
package org.jbpm.form.builder.ng.client.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jbpm.form.builder.ng.client.view.canvas.CanvasViewImpl;
import org.jbpm.form.builder.ng.client.view.palette.AnimatedPaletteViewImpl;
import org.jbpm.form.builder.ng.client.view.palette.PalettePresenter;
import org.jbpm.form.builder.ng.client.view.palette.PaletteView;
import org.jbpm.form.builder.ng.model.client.menu.FBMenuItem;
import org.jbpm.form.builder.ng.model.common.reflect.ReflectionHelper;
import org.jbpm.form.builder.ng.model.shared.menu.MenuItemDescription;
import org.jbpm.form.builder.ng.shared.events.MenuItemAddedEvent;
import org.uberfire.client.mvp.PlaceManager;

/**
 * Main view. Uses UIBinder to define the correct position of components
 */
@Dependent
public class FormBuilderViewImpl extends AbsolutePanel implements FormBuilderPresenter.FormBuilderView{

    @Inject
    private UiBinder<Widget, FormBuilderViewImpl> uiBinder;


    @Inject
    private PlaceManager placeManager;

    private FormBuilderPresenter presenter;
    
    public @UiField(provided=true) ScrollPanel menuView;
    public @UiField(provided=true) ScrollPanel layoutView;

    @Override
    public void init(final FormBuilderPresenter presenter) {
        this.presenter = presenter;
        init();
    }

    protected final void init() {
            menuView = new AnimatedPaletteViewImpl();
            layoutView = new CanvasViewImpl();
            //initWidget(uiBinder.createAndBindUi(this));
            menuView.setAlwaysShowScrollBars(true);
            menuView.setSize("235px", "100%");
            layoutView.setSize("700px", "700px");
            layoutView.setAlwaysShowScrollBars(true);
            add(uiBinder.createAndBindUi(this));
            //adopt(this);
//            
//            int fullHeight = Window.getClientHeight();
//            String height = "" + (fullHeight - 80) + "px";
//            String smallerHeight = "" + (fullHeight - 105) + "px";
            //treeView.setHeight("100%");
           // menuView.setHeight("100%");
//            editionView.setHeight("100%");
            //ioAssociationView.setHeight("100%");
           // layoutView.setHeight(smallerHeight);
            System.out.println("XXXX Components Initialized Cleaning up menu" + this.hashCode());
            ((PaletteView)menuView).removeAllItems();
            
    }
    
    public void addItem(@Observes MenuItemAddedEvent event) {
        try {
            System.out.println(" XXXXX An Item was added");
            String group = event.getGroupName();
            MenuItemDescription menuItemDescription = event.getMenuItemDescription();
            Object newInstance = ReflectionHelper.newInstance(menuItemDescription.getClassName());
            FBMenuItem item = (FBMenuItem) newInstance;
            
            ((PaletteView)menuView).addItem(group, item);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PalettePresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ScrollPanel getMenuView() {
        return menuView;
    }

    public ScrollPanel getLayoutView() {
        return layoutView;
    }

    public AbsolutePanel getPanel(){
        return this;
    }





}
