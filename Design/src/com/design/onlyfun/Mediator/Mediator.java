package com.design.onlyfun.Mediator;
/**
 *  An abstract Mediator
 */
public interface Mediator  {
    public void Register(Colleague c, String type);
    public void Changed(String type);
}