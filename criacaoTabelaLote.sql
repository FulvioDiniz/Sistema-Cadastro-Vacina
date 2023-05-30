CREATE TABLE public.lote
(
    codigo serial NOT NULL,
    validade date,
    nro_doses_do_lote integer,
    nro_doses_atual integer,
    situacao text,
    codigo_vacina integer,
    PRIMARY KEY (codigo)
);

ALTER TABLE public.lote
    ADD FOREIGN KEY (codigo_vacina)
    REFERENCES public.vacina (codigo)
    NOT VALID;
