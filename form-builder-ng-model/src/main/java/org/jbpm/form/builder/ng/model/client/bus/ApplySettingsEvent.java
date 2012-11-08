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
package org.jbpm.form.builder.ng.model.client.bus;


import org.jbpm.form.builder.ng.model.client.SettingsDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Tells whoever asked that the form representation of the current client edition has been loaded
 */
public class ApplySettingsEvent extends
        GwtEvent<ApplySettingsHandler> {

    public static final Type<ApplySettingsHandler> TYPE = new Type<ApplySettingsHandler>();
    
    
    private final SettingsDTO settings;
    
    public ApplySettingsEvent( SettingsDTO settings) {
        super();
        this.settings = settings;
    }

    public SettingsDTO getSettings() {
        return settings;
    }


    
    @Override
    public Type<ApplySettingsHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ApplySettingsHandler handler) {
        handler.onEvent(this);
    }

}
