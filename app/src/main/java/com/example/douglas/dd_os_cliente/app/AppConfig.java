package com.example.douglas.dd_os_cliente.app;

public class AppConfig {
	// Server user login url
	static String constantIP = "192.168.1.4";

    public static String URL_LOGIN = "http://"+constantIP+"/android_login_api/loginCliente.php";

    public static String URL_VERIFY_USER = "http://"+constantIP+"/android_login_api/verifyCliExist.php";

    public static String URL_EDIT_USER = "http://"+constantIP+"/android_login_api/alterarDetalhesUser.php";

    // Server user register url
	public static String URL_REGISTER_CLI = "http://"+constantIP+"/android_login_api/registerCliente.php";

    // Server user register url
    public static String URL_SERV_PEN = "http://"+constantIP+"/android_login_api/listaServPen.php";

    // Server user register url
    public static String URL_UPDATE_STATUS = "http://"+constantIP+"/android_login_api/alterarStatus.php";

    // Server user register url
    public static String URL_UPDATE_STATUS_REFRI = "http://"+constantIP+"/android_login_api/alterarStatusRefri.php";

    // Server user register url
    public static String URL_SERV_PEN_CLI = "http://"+constantIP+"/android_login_api/listaServPenCli.php";

    // Server user register url
    public static String URL_INSERIR_SERV_PEN_CLI = "http://"+constantIP+"/android_login_api/inserirServicePen.php";

    // Server user register url
    public static String URL_INSERIR_DESCRI_AR = "http://"+constantIP+"/android_login_api/inserirDescriAr.php";

    // Server user register url
    public static String URL_GET_All_AR_CLI = "http://"+constantIP+"/android_login_api/listaAllArCli.php";

    // Server user register url
    public static String URL_GET_AR_CLI = "http://"+constantIP+"/android_login_api/retornaArCli.php";

    // Server user register url
    public static String URL_GET_AR_SERV_CLI = "http://"+constantIP+"/android_login_api/getArForQRcode.php";

    // Server user register url
    public static String URL_GET_SERVICES = "http://"+constantIP+"/android_login_api/listaServices.php";

    // Server user register url
    public static String URL_GET_PECS = "http://"+constantIP+"/android_login_api/listaPecs.php";
    // Server user register url
    public static String URL_GET_MARCA_AR = "http://"+constantIP+"/android_login_api/listaMarcas.php";

    // Server user register url
    public static String URL_GET_MODELO_AR = "http://"+constantIP+"/android_login_api/listaModelos.php";

    // Server user register url
    public static String URL_GET_TENCAO_AR = "http://"+constantIP+"/android_login_api/listaTencao.php";

    // Server user register url
    public static String URL_GET_NV_ECON_AR = "http://"+constantIP+"/android_login_api/listaNvEcon.php";

    // Server user register url
    public static String URL_ALTERAR_DESCRI_AR = "http://"+constantIP+"/android_login_api/alterarDescriAr.php";

    // Server user register url
    public static String URL_GET_BTUS_AR = "http://"+constantIP+"/android_login_api/listaBTus.php";

    //WebService CEP - Localização
    public static String URL_SERVICE_CEP_LOCATION = "http://ws.hubdodesenvolvedor.com.br/v2/cep2/?json";
    //WebService CEP - Localização
    public static String URL_SERVICE_CEP_VIACEP = "https://viacep.com.br/ws/";
    //Cpf buscando nome
    public static String URL_SERVICE_CPF_NAME = "https://ws.hubdodesenvolvedor.com.br/v2/cpf";
    //TOKEN DO ACESSO AO SITE
    public static String TOKEN_hubdodesenvolvedor = "8178725LJWfzcEyRo14766440";






}
