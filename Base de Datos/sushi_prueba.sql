--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    rut_cliente text NOT NULL,
    nombre text
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: contacto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contacto (
    telefono numeric NOT NULL,
    rut_cliente text NOT NULL
);


ALTER TABLE public.contacto OWNER TO postgres;

--
-- Name: direcciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.direcciones (
    direccion text NOT NULL,
    rut_cliente text NOT NULL
);


ALTER TABLE public.direcciones OWNER TO postgres;

--
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empleado (
    rut_empleado text NOT NULL,
    nombre text,
    rol text,
    contrasenia numeric NOT NULL
);


ALTER TABLE public.empleado OWNER TO postgres;

--
-- Name: orden_de_compra; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orden_de_compra (
    id_orden numeric NOT NULL,
    fecha date,
    subtotal numeric,
    rut_empleado text,
    rut_cliente text
);


ALTER TABLE public.orden_de_compra OWNER TO postgres;

--
-- Name: producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto (
    id_producto numeric NOT NULL,
    nombre_producto text,
    stock numeric,
    precio_unitario numeric,
    unidad_de_medida text,
    fecha_elaboracion date,
    tipo text
);


ALTER TABLE public.producto OWNER TO postgres;

--
-- Name: proveedor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proveedor (
    rut_proveedor text NOT NULL,
    nombre_proveedor text
);


ALTER TABLE public.proveedor OWNER TO postgres;

--
-- Name: registro_abastecimiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_abastecimiento (
    numero_compra text NOT NULL,
    fecha date NOT NULL,
    rut_empleado text NOT NULL,
    rut_proveedor text NOT NULL
);


ALTER TABLE public.registro_abastecimiento OWNER TO postgres;

--
-- Name: registro_abastecimiento_contiene_producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_abastecimiento_contiene_producto (
    num_compra numeric NOT NULL,
    id_producto numeric NOT NULL,
    cantidad numeric
);


ALTER TABLE public.registro_abastecimiento_contiene_producto OWNER TO postgres;

--
-- Name: registro_de_venta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_de_venta (
    id_venta numeric NOT NULL,
    total numeric,
    fecha_de_pago date,
    id_orden numeric,
    rut_cliente text,
    rut_empleado text
);


ALTER TABLE public.registro_de_venta OWNER TO postgres;

--
-- Name: registro_despacho; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_despacho (
    num_despacho numeric NOT NULL,
    fecha date,
    receptor text,
    rut_empleado text,
    direccion text
);


ALTER TABLE public.registro_despacho OWNER TO postgres;

--
-- Name: registro_venta_contiene_despacho; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_venta_contiene_despacho (
    id_orden numeric NOT NULL,
    num_despacho numeric NOT NULL,
    cantidad numeric
);


ALTER TABLE public.registro_venta_contiene_despacho OWNER TO postgres;

--
-- Name: registro_venta_contiene_producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.registro_venta_contiene_producto (
    id_orden numeric NOT NULL,
    id_producto numeric NOT NULL,
    cantidad numeric
);


ALTER TABLE public.registro_venta_contiene_producto OWNER TO postgres;

--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (rut_cliente, nombre) FROM stdin;
\.


--
-- Data for Name: contacto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contacto (telefono, rut_cliente) FROM stdin;
\.


--
-- Data for Name: direcciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.direcciones (direccion, rut_cliente) FROM stdin;
\.


--
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.empleado (rut_empleado, nombre, rol, contrasenia) FROM stdin;
269188057	Eliam Villegas	DBA	123456
\.


--
-- Data for Name: orden_de_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orden_de_compra (id_orden, fecha, subtotal, rut_empleado, rut_cliente) FROM stdin;
\.


--
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producto (id_producto, nombre_producto, stock, precio_unitario, unidad_de_medida, fecha_elaboracion, tipo) FROM stdin;
\.


--
-- Data for Name: proveedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proveedor (rut_proveedor, nombre_proveedor) FROM stdin;
\.


--
-- Data for Name: registro_abastecimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_abastecimiento (numero_compra, fecha, rut_empleado, rut_proveedor) FROM stdin;
\.


--
-- Data for Name: registro_abastecimiento_contiene_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_abastecimiento_contiene_producto (num_compra, id_producto, cantidad) FROM stdin;
\.


--
-- Data for Name: registro_de_venta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_de_venta (id_venta, total, fecha_de_pago, id_orden, rut_cliente, rut_empleado) FROM stdin;
\.


--
-- Data for Name: registro_despacho; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_despacho (num_despacho, fecha, receptor, rut_empleado, direccion) FROM stdin;
\.


--
-- Data for Name: registro_venta_contiene_despacho; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_venta_contiene_despacho (id_orden, num_despacho, cantidad) FROM stdin;
\.


--
-- Data for Name: registro_venta_contiene_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.registro_venta_contiene_producto (id_orden, id_producto, cantidad) FROM stdin;
\.


--
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (rut_cliente);


--
-- Name: contacto contacto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacto
    ADD CONSTRAINT contacto_pkey PRIMARY KEY (telefono, rut_cliente);


--
-- Name: direcciones direcciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_pkey PRIMARY KEY (direccion, rut_cliente);


--
-- Name: empleado empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (rut_empleado);


--
-- Name: orden_de_compra orden_de_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_de_compra
    ADD CONSTRAINT orden_de_compra_pkey PRIMARY KEY (id_orden);


--
-- Name: producto producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (id_producto);


--
-- Name: proveedor proveedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (rut_proveedor);


--
-- Name: registro_abastecimiento_contiene_producto registro_abastecimiento_contiene_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_abastecimiento_contiene_producto
    ADD CONSTRAINT registro_abastecimiento_contiene_producto_pkey PRIMARY KEY (num_compra, id_producto);


--
-- Name: registro_abastecimiento registro_abastecimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_abastecimiento
    ADD CONSTRAINT registro_abastecimiento_pkey PRIMARY KEY (numero_compra);


--
-- Name: registro_de_venta registro_de_venta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_de_venta
    ADD CONSTRAINT registro_de_venta_pkey PRIMARY KEY (id_venta);


--
-- Name: registro_despacho registro_despacho_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_despacho
    ADD CONSTRAINT registro_despacho_pkey PRIMARY KEY (num_despacho);


--
-- Name: registro_venta_contiene_despacho registro_venta_contiene_despacho_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_venta_contiene_despacho
    ADD CONSTRAINT registro_venta_contiene_despacho_pkey PRIMARY KEY (id_orden, num_despacho);


--
-- Name: registro_venta_contiene_producto registro_venta_contiene_producto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_venta_contiene_producto
    ADD CONSTRAINT registro_venta_contiene_producto_pkey PRIMARY KEY (id_orden, id_producto);


--
-- Name: contacto contacto_rut_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacto
    ADD CONSTRAINT contacto_rut_cliente_fkey FOREIGN KEY (rut_cliente) REFERENCES public.cliente(rut_cliente);


--
-- Name: direcciones direcciones_rut_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.direcciones
    ADD CONSTRAINT direcciones_rut_cliente_fkey FOREIGN KEY (rut_cliente) REFERENCES public.cliente(rut_cliente);


--
-- Name: orden_de_compra orden_de_compra_rut_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_de_compra
    ADD CONSTRAINT orden_de_compra_rut_cliente_fkey FOREIGN KEY (rut_cliente) REFERENCES public.cliente(rut_cliente);


--
-- Name: orden_de_compra orden_de_compra_rut_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_de_compra
    ADD CONSTRAINT orden_de_compra_rut_empleado_fkey FOREIGN KEY (rut_empleado) REFERENCES public.empleado(rut_empleado);


--
-- Name: registro_abastecimiento registro_abastecimiento_rut_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_abastecimiento
    ADD CONSTRAINT registro_abastecimiento_rut_empleado_fkey FOREIGN KEY (rut_empleado) REFERENCES public.empleado(rut_empleado);


--
-- Name: registro_abastecimiento registro_abastecimiento_rut_proveedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_abastecimiento
    ADD CONSTRAINT registro_abastecimiento_rut_proveedor_fkey FOREIGN KEY (rut_proveedor) REFERENCES public.proveedor(rut_proveedor);


--
-- Name: registro_de_venta registro_de_venta_rut_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_de_venta
    ADD CONSTRAINT registro_de_venta_rut_cliente_fkey FOREIGN KEY (rut_cliente) REFERENCES public.cliente(rut_cliente) NOT VALID;


--
-- Name: registro_de_venta registro_de_venta_rut_empleado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_de_venta
    ADD CONSTRAINT registro_de_venta_rut_empleado_fkey FOREIGN KEY (rut_empleado) REFERENCES public.empleado(rut_empleado) NOT VALID;


--
-- Name: registro_despacho rut_empleado; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.registro_despacho
    ADD CONSTRAINT rut_empleado FOREIGN KEY (rut_empleado) REFERENCES public.empleado(rut_empleado);


--
-- PostgreSQL database dump complete
--

