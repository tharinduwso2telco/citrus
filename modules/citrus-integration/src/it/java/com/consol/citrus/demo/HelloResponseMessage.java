/*
 * Copyright 2006-2009 ConSol* Software GmbH.
 * 
 * This file is part of Citrus.
 * 
 *  Citrus is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Citrus is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Citrus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.consol.citrus.demo;

public class HelloResponseMessage {
    private String messageId;
    private String correlationId;
    private String user;
    private String text;
    
    private String exception;
    
    String xmlns = "http://www.consol.de/schemas/samples/sayHello.xsd";
    
    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }
    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    /**
     * @return the correlationId
     */
    public String getCorrelationId() {
        return correlationId;
    }
    /**
     * @param correlationId the correlationId to set
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @param exception the exception to set
     */
    public void setException(String exception) {
        this.exception = exception;
    }
    /**
     * @return the exception
     */
    public String getException() {
        return exception;
    }
    /**
     * @return the xmlns
     */
    public String getXmlns() {
        return xmlns;
    }
    /**
     * @param xmlns the xmlns to set
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }
    
}
