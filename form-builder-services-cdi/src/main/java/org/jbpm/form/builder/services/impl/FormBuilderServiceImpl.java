/*
 * Copyright 2012 JBoss by Red Hat.
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
package org.jbpm.form.builder.services.impl;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import org.jbpm.form.builder.ng.model.client.FormBuilderException;
import org.jbpm.form.builder.ng.model.client.Settings;
import org.jbpm.form.builder.ng.model.shared.api.FormItemRepresentation;
import org.jbpm.form.builder.ng.model.shared.api.FormRepresentation;
import org.jbpm.form.builder.ng.model.shared.form.FormEncodingException;
import org.jbpm.form.builder.services.api.FormBuilderService;
import org.jbpm.form.builder.services.encoders.FormEncodingServerFactory;
import org.jbpm.form.builder.services.tasks.TaskRef;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class FormBuilderServiceImpl implements FormBuilderService{

    public void getMenuItems() throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getMenuOptions() throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String saveForm(FormRepresentation form) throws FormBuilderException {
        String encode = null;
        try {
            encode = FormEncodingServerFactory.getEncoder().encode(form);
            
        } catch (FormEncodingException ex) {
            Logger.getLogger(FormBuilderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encode;
    }

    public void saveFormItem(FormItemRepresentation formItem, String formItemName) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteForm(FormRepresentation form) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFile(String url) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteFormItem(String formItemName, FormItemRepresentation formItem) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void generateForm(FormRepresentation form, String language, Map<String, Object> inputs) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getExistingIoAssociations(String filter) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void selectIoAssociation(String pkgName, String processName, String taskName) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getExistingValidations() throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getForm(String formName) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getForms() throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void populateRepresentationFactory() throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadFormTemplate(FormRepresentation form, String language) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getCurrentRoles(RolesResponseHandler handler) throws FormBuilderException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FormRepresentation toBasicForm(TaskRef task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUploadFileURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUploadActionURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPackageName(String packageName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void applySettings(Settings settings) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadSettings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFormDisplay() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public FormRepresentation loadForm(String json) {
        FormRepresentation formRep = null;
        try {
            formRep = FormEncodingServerFactory.getDecoder().decode(json);
            
        } catch (FormEncodingException ex) {
            Logger.getLogger(FormBuilderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formRep;
    }
    
}
