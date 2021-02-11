package com.geninvo.backend.config;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageableConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PageableConfiguration.class);

    @Value("${app.pageable.page.index.zero-based}")
    private boolean isZeroBasedPageIndex;
    @Value("${app.pageable.page.size.default}")
    private int defaultPageSize;
    @Value("${app.pageable.page.size.max}")
    private int maxPageSize;

    @PostConstruct
    public void setup() {
        PageRequestBuilder.DEFAULT_PAGE_NUMBER = isZeroBasedPageIndex ? 0 : 1;
        if (logger.isInfoEnabled())
            logger.info("Pagination zero-based page index? " + isZeroBasedPageIndex);
        PageRequestBuilder.DEFAULT_PAGE_SIZE = defaultPageSize;
        if (logger.isInfoEnabled())
            logger.info("Pagination default page size: " + PageRequestBuilder.DEFAULT_PAGE_SIZE);
        PageRequestBuilder.MAX_PAGE_SIZE = maxPageSize;
        if (logger.isInfoEnabled())
            logger.info("Pagination max page size: " + PageRequestBuilder.MAX_PAGE_SIZE);
    }

}