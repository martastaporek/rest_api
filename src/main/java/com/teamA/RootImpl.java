package com.teamA;


public class RootImpl implements Root {

    public static Root getInstance() {
        return new RootImpl();
    }

    private RootImpl() {}

    @Override
    public void run() {

    }

}
