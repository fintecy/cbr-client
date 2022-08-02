package org.fintecy.md.cbr;

import org.fintecy.md.cbr.model.CbrCurrency;
import org.fintecy.md.cbr.model.Currency;
import org.fintecy.md.cbr.model.ExchangeRate;
import org.fintecy.md.cbr.model.InterestRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author batiaev
 * @see <a href="https://www.cbr.ru/development/sxml/">docs</a>
 */
public interface CbrApi {
    String ROOT_PATH = "https://www.cbr.ru/scripts/";

    /**
     * @return latest daily rates
     * @see <a href="https://www.cbr.ru/scripts/XML_daily.asp">daily</a>
     */
    default List<ExchangeRate> rates() {
        return rates(false);
    }

    /**
     * @param monthly if true - return latest monthly rates
     * @return latest monthly/daily rates
     * @see <a href="https://www.cbr.ru/scripts/XML_daily.asp?d=1">monthly</a>
     */
    @Deprecated
    List<ExchangeRate> rates(boolean monthly);

    /**
     * @param date cob date
     * @return list of close of business rates from cbr
     * @see <a href="https://www.cbr.ru/scripts/XML_daily.asp?date_req=04/04/2002&d=1">monthly</a>
     * @see <a href="https://www.cbr.ru/scripts/XML_daily.asp?date_req=04/04/2002&d=0">daily</a>
     */
    List<ExchangeRate> rates(LocalDate date);

    /**
     * @param from start date
     * @param to   end date
     * @param code cbr currency code
     * @return exchange rates for currency
     * @see <a href="https://www.cbr.ru/scripts/xml_metall.asp?date_req1=01/07/2001&date_req2=13/07/2001">metals</a>
     * @see <a href="https://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=14/03/2001&VAL_NM_RQ=R01235">fiat</a>
     */
    List<ExchangeRate> rates(String code, LocalDate from, LocalDate to);

    /**
     * @param from start date
     * @param to   end date
     * @return deposit rates per date
     * @see <a href="https://www.cbr.ru/scripts/xml_depo.asp?date_req1=01/07/2001&date_req2=13/07/2001">docs</a>
     */
    Map<LocalDate, List<InterestRate>> depositRates(LocalDate from, LocalDate to);

    /**
     * @return list of supported currencies
     * @see <a href="https://www.cbr.ru/scripts/XML_val.asp">docs</a>
     * @see <a href="https://www.cbr.ru/scripts/XML_valFull.asp">docs</a>
     * @see <a href="https://www.cbr.ru/scripts/XML_valFull.asp?d=0">currency codes for daily rates</a>
     * @see <a href="https://www.cbr.ru/scripts/XML_valFull.asp?d=1">currency codes for monthly rates</a>
     */
    List<CbrCurrency> supportedCurrencies();
}
