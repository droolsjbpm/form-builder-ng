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
package org.jbpm.form.builder.ng.model.shared.menu.items;

import java.util.List;

import org.jbpm.form.builder.ng.model.client.effect.FBFormEffect;
import org.jbpm.form.builder.ng.model.client.form.FBFormItem;
import org.jbpm.form.builder.ng.model.client.form.items.HTMLFormItem;
import org.jbpm.form.builder.ng.model.client.resources.FormBuilderResources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Label;
import com.gwtent.reflection.client.Reflectable;
import org.jbpm.form.builder.ng.model.client.CommonGlobals;
import org.jbpm.form.builder.ng.model.client.menu.FBMenuItem;

@Reflectable
public class HTMLMenuItem extends FBMenuItem {

    public HTMLMenuItem() {
        super();
    }
    
    public HTMLMenuItem(List<FBFormEffect> formEffects) {
        super(formEffects);
    }

    @Override
    protected ImageResource getIconUrl() {
        return FormBuilderResources.INSTANCE.html();
    }

    @Override
    public Label getDescription() {
        return new Label(CommonGlobals.getInstance().getI18n().MenuItemHTMLScript());
    }

    @Override
    public FBMenuItem cloneWidget() {
        return clone(new HTMLMenuItem());
    }

    @Override
    public FBFormItem buildWidget() {
        return build(new HTMLFormItem());
    }

}