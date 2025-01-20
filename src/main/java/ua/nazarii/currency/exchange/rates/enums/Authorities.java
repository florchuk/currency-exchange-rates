package ua.nazarii.currency.exchange.rates.enums;

public enum Authorities {
    USER,
    PROVIDER;

    public final String getAuthority() {
        return this.name();
    }
}
