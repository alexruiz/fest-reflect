/*
 * Created on May 18, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Understands a Jedi.
 * 
 * @author Alex Ruiz
 */
public class Jedi extends Person {

  private static List<String> commonPowers = new ArrayList<String>();

  private final List<String> powers = new ArrayList<String>();
  private boolean master;

  public Jedi(String name) {
    super(name);
  }

  public void addPower(String power) {
    powers.add(power);
  }

  public String powerAt(int index) {
    return powers.get(index);
  }

  public int powerCount() {
    return powers.size();
  }

  public void clearPowers() {
    powers.clear();
  }

  public void makeMaster() {
    master = true;
  }

  public List<String> powers() {
    return getPowers();
  }

  public List<String> getPowers() {
    return new ArrayList<String>(powers);
  }

  public void setPowers(List<String> newPowers) {
    powers.clear();
    powers.addAll(newPowers);
  }

  public List<String> powersThatStartWith(String prefix) {
    List<String> sub = new ArrayList<String>();
    for (String power : powers)
      if (power != null && power.startsWith(prefix)) sub.add(power);
    return sub;
  }

  public boolean isMaster() {
    return master;
  }

  public void throwRuntimeException() {
    throw new IllegalStateException("Somehow I got in an illegal state");
  }

  public void throwCheckedException() throws Exception {
    throw new Exception("I don't know what's wrong");
  }

  public static void addCommonPower(String power) {
    commonPowers.add(power);
  }

  public static String commonPowerAt(int index) {
    return commonPowers.get(index);
  }

  public static int commonPowerCount() {
    return commonPowers.size();
  }

  public static void clearCommonPowers() {
    commonPowers.clear();
  }

  public static List<String> commonPowers() {
    return new ArrayList<String>(commonPowers);
  }

  public static List<String> commonPowersThatStartWith(String prefix) {
    List<String> sub = new ArrayList<String>();
    for (String power : commonPowers)
      if (power != null && power.startsWith(prefix)) sub.add(power);
    return sub;
  }
}