package com.mitchtalmadge.shillorkill.service;

import com.mitchtalmadge.shillorkill.domain.model.CMCTickerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinMarketCapService {

    private final LogService logService;

    @Autowired
    public CoinMarketCapService(LogService logService) {
        this.logService = logService;
    }

    /**
     * The URL to the tickers endpoint.
     */
    private static final String TICKER_URL = "https://api.coinmarketcap.com/v1/ticker/";

    /**
     * A cache of CoinMarketCap tickers.
     */
    private CMCTickerDTO[] tickers = new CMCTickerDTO[0];

    /**
     * @return An array of all CoinMarketCap Tickers.
     */
    public CMCTickerDTO[] getTickers() {
        return tickers;
    }

    /**
     * Using the CoinMarketCap API, fetches all Tickers and stores them in the cache every 10 minutes.
     */
    @Scheduled(fixedRate = 10 * 60_000)
    @Async
    protected void updateTickers() {
        logService.logDebug(getClass(), "Updating CMC Tickers...");
        RestTemplate restTemplate = new RestTemplate();
        tickers = restTemplate.getForObject(TICKER_URL, CMCTickerDTO[].class);
        logService.logDebug(getClass(), "Update Complete. " + tickers.length + " Tickers cached.");
    }

}
