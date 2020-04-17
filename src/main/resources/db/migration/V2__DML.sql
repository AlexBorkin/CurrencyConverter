INSERT INTO public.currency(
	"CurrencyCode", "Description", "NumCode", "Nominal")
	VALUES ('RUR', 'Российский рубль', '643', 1);

INSERT INTO public."exchRates"(
    "DateRate", "CurrencyCode", "CurrencyRate")
    VALUES ('1900-01-01', 'RUR', 1);

INSERT INTO public.role(
	"roleName", description)
	VALUES ('USER', 'USER');