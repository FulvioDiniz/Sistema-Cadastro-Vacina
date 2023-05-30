CREATE TABLE public.vacina
(
    codigo serial NOT NULL,
    nome text,
    descricao text,
    situacao text,
    PRIMARY KEY (codigo)
);
