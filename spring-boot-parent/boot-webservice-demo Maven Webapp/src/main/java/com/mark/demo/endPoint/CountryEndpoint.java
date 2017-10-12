package com.mark.demo.endPoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mark.demo.ws.Country;
import com.mark.demo.ws.Currency;
import com.mark.demo.ws.GetCountryRequest;
import com.mark.demo.ws.GetCountryResponse;

/*
*hxp(hxpwangyi@126.com)
*2017年10月12日
*
*/
@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://www.mark.com/webservice";

    //private CountryRepository countryRepository;

   /* @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }*/

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Country country=new Country();
        country.setCapital("北京");
        country.setCurrency(Currency.EUR);
        country.setName("中国");
        country.setPopulation(1000000);
        response.setCountry(country);

        return response;
    }
}