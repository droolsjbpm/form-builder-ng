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
package org.jbpm.form.builder.ng.shared.events;

import java.io.Serializable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.form.builder.ng.model.shared.menu.MenuItemDescription;

/**
 *
 */
@Portable
public class MenuItemAddedEvent implements Serializable{
    private MenuItemDescription menuItemDesc;
    private String groupName;

    public MenuItemAddedEvent(MenuItemDescription menuItem, String groupName) {
        this.menuItemDesc = menuItem;
        this.groupName = groupName;
    }

    public MenuItemAddedEvent() {
    }

    public MenuItemDescription getMenuItemDescription() {
        return menuItemDesc;
    }

    public String getGroupName() {
        return groupName;
    }
    
    
}
