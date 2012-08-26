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
package org.jbpm.form.builder.ng.client.view.display;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.IsWidget;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.jbpm.form.builder.ng.shared.FormServiceEntryPoint;
import org.jbpm.form.builder.services.api.FileException;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;

@Dependent
@WorkbenchScreen(identifier = "Form Display")
public class FormDisplayPresenter {

    @Inject
    private FormBuilderView view;
    @Inject
    private Caller<FormServiceEntryPoint> formServices;

    public interface FormBuilderView
            extends
            IsWidget {

    }

    @PostConstruct
    public void init() {
        
        
            
            
//            System.out.println("XXXXX  Calling List Menu Items" + this.hashCode());
//            
//            formServices.call(new RemoteCallback<Void>() {
//                @Override
//                public void callback(Void nothing) {
//                    System.out.println("XXXXX  RETURN load file  Items");
//                }
//            }).

            
           



            //
            //        bus.addHandler(RepresentationFactoryPopulatedEvent.TYPE, new RepresentationFactoryPopulatedHandler() {
            //            @Override
            //            public void onEvent(RepresentationFactoryPopulatedEvent event) {
            //                try {
            //                    service.getMenuItems();
            //                    service.getMenuOptions();
            //                } catch (FormBuilderException e) {
            //                    //implementation never throws this
            //                }
            //                List<GwtEvent<?>> events = setDataPanel(rootPanel);
            //
            //                //events are fired deferred since they might need that ui components are already attached
            //                fireEvents(events);
            //            }
            //        });
            //        populateRepresentationFactory(service);
            //        populateRepresentationFactory(service);

       
    }
    
    

    @WorkbenchPartTitle
    public String getTitle() {
        return "Form Display";
    }

    @WorkbenchPartView
    public IsWidget getView() {
        return view;
    }
}