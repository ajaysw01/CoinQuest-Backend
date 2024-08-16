package com.ajay.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ajay.model.Coin;
import com.ajay.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService{
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Value("${coingecko.api.key}")
    private String API_KEY;


//
//    @Override
//    public List<Coin> getCoinList(int page) throws Exception {
//        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+page;
//        RestTemplate restTemplate = new RestTemplate();
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);
//
//
//            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//            System.out.println(response.getBody());
//            List<Coin> coins = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});
//
//            return coins;
//
//        } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
//            System.err.println("Error: " + e);
//            // Handle error accordingly
//            throw new Exception("please wait for time because you are using free plan");
//        }
//
//    }

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            System.out.println(response.getBody()); // Debugging: Log the response body

            List<Coin> coins = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});

            return coins;

        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getResponseBodyAsString()); // Log full error response
            throw new Exception("API request failed: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            System.err.println("Server Error: " + e.getResponseBodyAsString()); // Log full error response
            throw new Exception("Server-side error occurred. Please try again later.");
        } catch (JsonProcessingException e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
            throw new Exception("Failed to parse API response. Please try again later.");
        }
    }


    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=usd&days="+days;

        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error: " + e);
            // Handle error accordingly
//            return null;
            throw new Exception("you are using free plan");
        }

    }

    private double convertToDouble(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Long) {
            return ((Long) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getName());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws JsonProcessingException {

        String baseUrl ="https://api.coingecko.com/api/v3/coins/"+coinId;

        System.out.println("------------------ get coin details base url "+baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-cg-demo-api-key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

//        Coin coins = objectMapper.readValue(response.getBody(), new TypeReference<>() {
//        });
//        coinRepository.save(coins);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode.get("image").get("large");
        System.out.println(jsonNode.get("image").get("large"));

        Coin coin = new Coin();

        coin.setId(jsonNode.get("id").asText());
        coin.setSymbol(jsonNode.get("symbol").asText());
        coin.setName(jsonNode.get("name").asText());
        coin.setImage(jsonNode.get("image").get("large").asText());

        JsonNode marketData = jsonNode.get("market_data");

        coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
        coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
        coin.setMarketCapRank(jsonNode.get("market_cap_rank").asInt());
        coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
        coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
        coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
        coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
        coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
        coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
        coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
        coin.setCirculatingSupply(marketData.get("circulating_supply").asLong());
        coin.setTotalSupply(marketData.get("total_supply").asLong());

        coinRepository.save(coin);
        return response.getBody();
    }

    @Override
    public Coin findById(String coinId) throws Exception{
        Optional<Coin> optionalCoin = coinRepository.findById(coinId);
        if(optionalCoin.isEmpty()) throw new Exception("invalid coin id");
        return  optionalCoin.get();
    }

    @Override
    public String searchCoin(String keyword) {
        String baseUrl ="https://api.coingecko.com/api/v3/search?query="+keyword;

        HttpHeaders headers = new HttpHeaders();
    //    headers.set("x-cg-demo-api-key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        return response.getBody();
    }

//    @Override
//    public String getTop50CoinsByMarketCapRank() {
//        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&page=1&per_page=50";
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setConnectTimeout(10000); // 10 seconds
//        requestFactory.setReadTimeout(10000); // 10 seconds
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);
//
//            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//            return response.getBody();
//
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            System.err.println("Error: " + e);
//            // Handle error accordingly
//            return null;
//        }
//
//    }

    @Override
    public String getTop50CoinsByMarketCapRank() {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&page=1&per_page=50";
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000); // 10 seconds
        requestFactory.setReadTimeout(10000); // 10 seconds

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        try {
            HttpHeaders headers = new HttpHeaders();
            // Remove the API key header if not needed for the free API
            // headers.set("x-cg-pro-api-key", API_KEY);  // Make sure this key matches the required header key by the API

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error: " + e.getResponseBodyAsString());
            // Handle error accordingly
            return null;
        }
    }

    @Override
    public String getTreadingCoins() {
        String url = "https://api.coingecko.com/api/v3/search/trending";

        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.err.println("Error: " + e);
            // Handle error accordingly
            return null;
        }
    }
}
