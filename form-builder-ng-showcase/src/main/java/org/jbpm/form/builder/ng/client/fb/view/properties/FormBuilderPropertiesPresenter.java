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
package org.jbpm.form.builder.ng.client.fb.view.properties;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jboss.errai.ioc.client.api.Caller;
import org.jbpm.form.builder.ng.shared.FormServiceEntryPoint;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberView;

@Dependent
@WorkbenchScreen(identifier = "Form Builder - Properties")
public class FormBuilderPropertiesPresenter {

    @Inject
    private FormBuilderView view;
    @Inject
    private Caller<FormServiceEntryPoint> formServices;
    
    

    public interface FormBuilderView
            extends
            UberView<FormBuilderPropertiesPresenter> {

        ScrollPanel getPropertiesView();

        
        AbsolutePanel getPanel();
    }

    @PostConstruct
    public void init() {
        
    }
    
    
    
    @WorkbenchPartTitle
    public String getTitle() {
        return "Form Builder - Properties";
    }

    @WorkbenchPartView
    public UberView<FormBuilderPropertiesPresenter> getView() {
        return view;
    }
}
