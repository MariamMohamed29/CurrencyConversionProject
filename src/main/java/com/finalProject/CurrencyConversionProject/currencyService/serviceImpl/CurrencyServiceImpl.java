package com.finalProject.CurrencyConversionProject.currencyService.serviceImpl;

import com.finalProject.CurrencyConversionProject.currencyService.CurrencyServiceInterface;
import com.finalProject.CurrencyConversionProject.dto.AmountConversionDto;
import com.finalProject.CurrencyConversionProject.dto.FavoriteCurrenciesDto;
import com.finalProject.CurrencyConversionProject.dto.TwoCurrenciesComparisonDto;
import com.finalProject.CurrencyConversionProject.model.constants.Currencies;
import com.finalProject.CurrencyConversionProject.apiService.CurrenncyApiServiceInterface;
import com.finalProject.CurrencyConversionProject.validation.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyServiceInterface {
    @Autowired
    InputValidation inputValidation;
    @Autowired
    private CurrenncyApiServiceInterface currencyRepository;


    @Override
    public AmountConversionDto convertAmount(String base, String target, Double amount){
        base.toUpperCase();
        target.toUpperCase();
        this.inputValidation.checkCurrency(base);
        this.inputValidation.checkCurrency(target);
        this.inputValidation.checkAmount(amount);

        AmountConversionDto responseObject = (AmountConversionDto) this.currencyRepository.convertAmount(base, target, amount);;
        return responseObject;
    }

    @Override
    public FavoriteCurrenciesDto compareCurrencies(List<String> currencies, String base) {
        base.toUpperCase();
        currencies.stream().forEach(currency -> currency.toUpperCase());
        this.inputValidation.checkList(currencies, currencies.size());
        this.inputValidation.checkCurrency(base);

        FavoriteCurrenciesDto responseObject = (FavoriteCurrenciesDto) this.currencyRepository.compareCurrencies(base);

        Map<String ,Double> finalResponseMap = new HashMap<>();
        Map<String, Double> responseMap = responseObject.getConversion_rates();

        currencies.stream().forEach(currency -> finalResponseMap.put(currency, responseMap.get(currency)));

        FavoriteCurrenciesDto finalResponseObject = FavoriteCurrenciesDto.builder()
                .conversion_rates(finalResponseMap)
                .build();

        return finalResponseObject;
    }
    @Override
    public List<Map<String, String>> getCurrencies() {
        List<Map<String, String>> currencies = Currencies.getCurrencies();
        return currencies;
    }

    @Override
    public TwoCurrenciesComparisonDto compareTwoCurrencies(String base, Double amount,String target1,String target2) {
        base.toUpperCase();
        target1.toUpperCase();
        target2.toUpperCase();
        this.inputValidation.checkCurrency(base);
        this.inputValidation.checkAmount(amount);
        this.inputValidation.checkCurrency(target1);
        this.inputValidation.checkCurrency(target2);

        AmountConversionDto response1 = (AmountConversionDto) this.currencyRepository.convertAmount(base, target1, amount);
        AmountConversionDto response2 = (AmountConversionDto) this.currencyRepository.convertAmount(base, target2, amount);

        TwoCurrenciesComparisonDto finalResponse = TwoCurrenciesComparisonDto.builder()
                .firstTargetCurrency(response1)
                .secondTargetCurrency(response2)
                .build();
        return finalResponse;
    }


}
