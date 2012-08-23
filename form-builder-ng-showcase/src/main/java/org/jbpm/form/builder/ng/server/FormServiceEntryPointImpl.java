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
package org.jbpm.form.builder.ng.server;

import org.jbpm.form.builder.ng.shared.FormServiceEntryPoint;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jboss.errai.bus.server.annotations.Service;
import org.jbpm.form.builder.services.api.MenuService;
import org.jbpm.form.builder.services.api.MenuServiceException;
import org.jbpm.form.builder.services.encoders.FormRepresentationDecoderImpl;
import org.jbpm.form.builder.services.encoders.FormRepresentationEncoderImpl;
import org.jbpm.form.builder.ng.shared.events.MenuItemAddedEvent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import org.jbpm.form.builder.ng.model.shared.form.FormEncodingFactory;
import org.jbpm.form.builder.ng.model.shared.menu.MenuItemDescription;
import org.jbpm.form.builder.ng.model.shared.menu.MenuOptionDescription;

/**
 *
 * @author salaboy
 */
@Service
@ApplicationScoped
public class FormServiceEntryPointImpl implements FormServiceEntryPoint {

    @Inject
    private MenuService menuService;
    @Inject
    Event<MenuItemAddedEvent> itemAddedEvents;
    @Inject
    private BeanManager manager;

    @PostConstruct
    public void init() {
        FormEncodingFactory.register(new FormRepresentationEncoderImpl(), new FormRepresentationDecoderImpl());
        
    }

    @Override
    public List<MenuOptionDescription> listOptions() throws MenuServiceException {
        return menuService.listOptions();
    }

    @Override
    public void listMenuItems() throws MenuServiceException {
        Map<String, List<MenuItemDescription>> listMenuItems = menuService.listMenuItems();
        
        
        try {
            for (String groupName : listMenuItems.keySet()) {
                for (MenuItemDescription itemDesc : listMenuItems.get(groupName)) {
                        itemAddedEvents.fire(new MenuItemAddedEvent(itemDesc, groupName));
                }
            }
        } catch (Exception ex) {
            
            ex.printStackTrace();
            Logger.getLogger(FormServiceEntryPointImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Map<String, String> getFormBuilderProperties() throws MenuServiceException {
        return menuService.getFormBuilderProperties();
    }
}
