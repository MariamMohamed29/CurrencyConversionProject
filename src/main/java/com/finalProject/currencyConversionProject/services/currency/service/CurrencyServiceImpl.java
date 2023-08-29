package com.finalProject.currencyConversionProject.services.currency.service;

import com.finalProject.currencyConversionProject.dto.PairCurrenciesConversionDto;
import com.finalProject.currencyConversionProject.services.CacheService;
import com.finalProject.currencyConversionProject.dto.AmountConversionDto;
import com.finalProject.currencyConversionProject.dto.FavoriteCurrenciesDto;
import com.finalProject.currencyConversionProject.dto.TwoCurrenciesComparisonDto;
import com.finalProject.currencyConversionProject.model.constants.Currencies;
import com.finalProject.currencyConversionProject.services.api.service.CurrenncyApiServiceInterface;
import com.finalProject.currencyConversionProject.validation.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyServiceInterface {
    @Autowired
    private InputValidation inputValidation;
    @Autowired
    private CurrenncyApiServiceInterface currenncyApiService;
    @Autowired
    private CacheService cacheService;


    @Override
    public AmountConversionDto convertAmount(String base, String target, Double amount) {
        evictAllcachesAtIntervals();

        this.convertToUpperCase(base, target);

        this.inputValidation.checkCurrency(base);
        this.inputValidation.checkCurrency(target);
        this.inputValidation.checkAmount(amount);

        PairCurrenciesConversionDto pairCurrenciesConversionDto = this.currenncyApiService.convertAmount(base, target, amount);
        Double conversionResult = amount * pairCurrenciesConversionDto.getConversion_rate();
        AmountConversionDto responseObject = AmountConversionDto.builder()
                .conversion_result((conversionResult)).build();
        return responseObject;
    }

    @Override
    public List<Double> compareCurrencies(List<String> favoriteCurrencies, String base) {
        evictAllcachesAtIntervals();

        List<String> currencies = this.convertToUpperCase(favoriteCurrencies, base);

        this.inputValidation.checkList(currencies, currencies.size());
        this.inputValidation.checkCurrency(base);

        FavoriteCurrenciesDto responseObject = this.currenncyApiService.compareCurrencies(base);

        Map<String, Double> responseMap = responseObject.getConversion_rates();


        List<Double> finalResponseObject = new ArrayList<>();
        currencies.stream().forEach(currency -> finalResponseObject.add(responseMap.get(currency)));

        return finalResponseObject;
    }

    @Override
    @Cacheable(value = "getCurrencies", key = "#root.methodName")
    public List<Map<String, String>> getCurrencies() {
        List<Map<String, String>> currencies = Currencies.getCurrencies();
        return currencies;
    }

    @Override
    public TwoCurrenciesComparisonDto compareTwoCurrencies(String base, Double amount, String target1, String target2) {
        evictAllcachesAtIntervals();

        this.convertToUpperCase(base, target1, target2);

        this.inputValidation.checkCurrency(base);
        this.inputValidation.checkAmount(amount);
        this.inputValidation.checkCurrency(target1);
        this.inputValidation.checkCurrency(target2);

        AmountConversionDto response1 = this.convertAmount(base, target1, amount);
        AmountConversionDto response2 = this.convertAmount(base, target2, amount);

        TwoCurrenciesComparisonDto finalResponse = TwoCurrenciesComparisonDto.builder()
                .firstTargetCurrency(response1)
                .secondTargetCurrency(response2)
                .build();
        return finalResponse;
    }

    private void convertToUpperCase(String... currency) {
        Arrays.stream(currency).collect(Collectors.toList()).stream()
                .forEach(cur -> cur.toUpperCase());
    }

    private List<String> convertToUpperCase(List<String> list, String... currency) {
        this.convertToUpperCase(currency);
        return list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    private void evictAllcachesAtIntervals() {
        this.cacheService.evictAllcachesAtIntervals();
    }
}
