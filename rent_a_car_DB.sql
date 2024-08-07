PGDMP      3    
            |         
   Rent_A_Car    16.3    16.3 !    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16732 
   Rent_A_Car    DATABASE     �   CREATE DATABASE "Rent_A_Car" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE "Rent_A_Car";
                postgres    false            �            1259    16795    book_book_id_seq    SEQUENCE     y   CREATE SEQUENCE public.book_book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.book_book_id_seq;
       public          postgres    false            �            1259    16740    book    TABLE     �  CREATE TABLE public.book (
    book_id integer DEFAULT nextval('public.book_book_id_seq'::regclass) NOT NULL,
    book_car_id integer NOT NULL,
    book_name text NOT NULL,
    book_idno text NOT NULL,
    book_mpno text NOT NULL,
    book_mail text,
    book_strt_date date NOT NULL,
    book_fnsh_date date NOT NULL,
    book_prc integer NOT NULL,
    book_note text,
    book_case text NOT NULL
);
    DROP TABLE public.book;
       public         heap    postgres    false    223            �            1259    16769    brand    TABLE     [   CREATE TABLE public.brand (
    brand_id integer NOT NULL,
    brand_name text NOT NULL
);
    DROP TABLE public.brand;
       public         heap    postgres    false            �            1259    16768    brand_brand_id_seq    SEQUENCE     �   CREATE SEQUENCE public.brand_brand_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.brand_brand_id_seq;
       public          postgres    false    218            �           0    0    brand_brand_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.brand_brand_id_seq OWNED BY public.brand.brand_id;
          public          postgres    false    217            �            1259    16787    car    TABLE     �   CREATE TABLE public.car (
    car_id integer NOT NULL,
    car_model_id integer NOT NULL,
    car_color text NOT NULL,
    car_km integer NOT NULL,
    car_plate text NOT NULL
);
    DROP TABLE public.car;
       public         heap    postgres    false            �            1259    16786    car_car_id_seq    SEQUENCE     �   CREATE SEQUENCE public.car_car_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.car_car_id_seq;
       public          postgres    false    222            �           0    0    car_car_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.car_car_id_seq OWNED BY public.car.car_id;
          public          postgres    false    221            �            1259    16778    model    TABLE     �   CREATE TABLE public.model (
    model_id integer NOT NULL,
    model_brand_id integer NOT NULL,
    model_name text NOT NULL,
    model_type text NOT NULL,
    model_year text NOT NULL,
    model_fuel text NOT NULL,
    model_gear text NOT NULL
);
    DROP TABLE public.model;
       public         heap    postgres    false            �            1259    16777    model_model_id_seq    SEQUENCE     �   CREATE SEQUENCE public.model_model_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.model_model_id_seq;
       public          postgres    false    220            �           0    0    model_model_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.model_model_id_seq OWNED BY public.model.model_id;
          public          postgres    false    219            �            1259    16733    users    TABLE     �   CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_name text NOT NULL,
    user_password text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            .           2604    16772    brand brand_id    DEFAULT     p   ALTER TABLE ONLY public.brand ALTER COLUMN brand_id SET DEFAULT nextval('public.brand_brand_id_seq'::regclass);
 =   ALTER TABLE public.brand ALTER COLUMN brand_id DROP DEFAULT;
       public          postgres    false    218    217    218            0           2604    16790 
   car car_id    DEFAULT     h   ALTER TABLE ONLY public.car ALTER COLUMN car_id SET DEFAULT nextval('public.car_car_id_seq'::regclass);
 9   ALTER TABLE public.car ALTER COLUMN car_id DROP DEFAULT;
       public          postgres    false    222    221    222            /           2604    16781    model model_id    DEFAULT     p   ALTER TABLE ONLY public.model ALTER COLUMN model_id SET DEFAULT nextval('public.model_model_id_seq'::regclass);
 =   ALTER TABLE public.model ALTER COLUMN model_id DROP DEFAULT;
       public          postgres    false    219    220    220            �          0    16740    book 
   TABLE DATA           �   COPY public.book (book_id, book_car_id, book_name, book_idno, book_mpno, book_mail, book_strt_date, book_fnsh_date, book_prc, book_note, book_case) FROM stdin;
    public          postgres    false    216   �#       �          0    16769    brand 
   TABLE DATA           5   COPY public.brand (brand_id, brand_name) FROM stdin;
    public          postgres    false    218   �#       �          0    16787    car 
   TABLE DATA           Q   COPY public.car (car_id, car_model_id, car_color, car_km, car_plate) FROM stdin;
    public          postgres    false    222   /$       �          0    16778    model 
   TABLE DATA           u   COPY public.model (model_id, model_brand_id, model_name, model_type, model_year, model_fuel, model_gear) FROM stdin;
    public          postgres    false    220   �$       �          0    16733    users 
   TABLE DATA           M   COPY public.users (user_id, user_name, user_password, user_role) FROM stdin;
    public          postgres    false    215   0%       �           0    0    book_book_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.book_book_id_seq', 7, true);
          public          postgres    false    223            �           0    0    brand_brand_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.brand_brand_id_seq', 10, true);
          public          postgres    false    217            �           0    0    car_car_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.car_car_id_seq', 7, true);
          public          postgres    false    221            �           0    0    model_model_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.model_model_id_seq', 7, true);
          public          postgres    false    219            4           2606    16797    book Book_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.book
    ADD CONSTRAINT "Book_pkey" PRIMARY KEY (book_id);
 :   ALTER TABLE ONLY public.book DROP CONSTRAINT "Book_pkey";
       public            postgres    false    216            2           2606    16739    users User_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (user_id);
 ;   ALTER TABLE ONLY public.users DROP CONSTRAINT "User_pkey";
       public            postgres    false    215            6           2606    16776    brand brand_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.brand
    ADD CONSTRAINT brand_pkey PRIMARY KEY (brand_id);
 :   ALTER TABLE ONLY public.brand DROP CONSTRAINT brand_pkey;
       public            postgres    false    218            :           2606    16794    car car_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (car_id);
 6   ALTER TABLE ONLY public.car DROP CONSTRAINT car_pkey;
       public            postgres    false    222            8           2606    16785    model model_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (model_id);
 :   ALTER TABLE ONLY public.model DROP CONSTRAINT model_pkey;
       public            postgres    false    220            �   N   x�E�1� ���_�HB�lxF����V:��`���Ӈ�E��rV�3KIUE�����gH=8n�̨@_��D�U��      �   A   x���t�IKT��M���t,.��S�M,*���24���O��LO/NM�2�J�K,�)����� �c�      �   c   x��A
�@D�u�)��5���2�%�H0d���akQ��'�o�P*��O�ZX\�h9T��c���eo`���Ui4y"�����\��W�c��x�      �   ~   x�3�4�t���W0��pq�prt��4202�tw����s�t��2��t�,��LDQgh�P������e�ih����Z������Rf�f��%���9B�	�s��*8]<]�]}`���qqq �,6      �      x�3�LL����442���b���� J,�     