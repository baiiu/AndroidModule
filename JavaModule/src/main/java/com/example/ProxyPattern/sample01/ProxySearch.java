package com.example.ProxyPattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 16:45
 * description:
 */
public class ProxySearch implements ISearch {

    private RealSearch mRealSearch;
    private Validator mValidator;
    private Logger mLogger;

    public ProxySearch() {
        this.mValidator = new Validator();
        this.mRealSearch = new RealSearch();
        this.mLogger = new Logger();
    }

    @Override public void doSearch() {
        if (mValidator.doVailidator()) {
            mRealSearch.doSearch();
            mLogger.log();
        }
    }
}
