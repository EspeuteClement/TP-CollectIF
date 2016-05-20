/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tp.dasi.collectif;

import javax.servlet.http.HttpServletRequest;
import service.ServiceMetier;

/**
 *
 * @author Element
 */
public abstract class Action {
    public abstract void execute(HttpServletRequest request);
}
