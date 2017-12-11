/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor.services;

/**
 *
 * @author Daniel Leistner
 */
public interface LocaleService {

	String get(String key);

	String get(String key, Object... args);
}
