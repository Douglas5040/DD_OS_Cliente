/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.douglas.dd_os_cliente.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.douglas.dd_os_cliente.controler.PecsCtrl;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.controler.ServicesCtrl;
import com.example.douglas.dd_os_cliente.controler.UserClienteCtrl;
import com.example.douglas.dd_os_cliente.controler.UserFuncionarioCtrl;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 52;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_USER_CLI = "user_cliente";
	private static final String TABLE_MY_SERV_PEN = "my_serv_pen";
	private static final String TABLE_OS = "ordem_service";
	private static final String TABLE_SERVICES = "services";
	private static final String TABLE_PECS = "pecs";
	private static final String TABLE_SERV_FUNC = "serv_func";
	private static final String TABLE_PCS_PROB_OS = "pcs_prob_os";
	private static final String TABLE_GUARDA_DADOS_OS = "guarda_dados_os";
	private static final String TABLE_AR_CLIENTE = "refrigeradores_cli";
	private static final String TABLE_MARCA = "marca";
	private static final String TABLE_MODELO = "modelo";
	private static final String TABLE_CAPACI_TERMI = "capaci_termi";
	private static final String TABLE_NV_ECON = "nivel_econ";
	private static final String TABLE_TENCAO = "tencao_tomada";

	// Ordem Srevice Table Columns names
	private static final String KEY_ID_OS = "id";
	private static final String KEY_ID_CLI = "id_cli";
	private static final String KEY_MATRI_FUNC = "matri_func";
	private static final String KEY_TIPO_MANU = "tipo_manu";
	private static final String KEY_OBS = "obs";
	private static final String KEY_DATA = "data";
	private static final String KEY_HORA_INI = "hora_ini";
	private static final String KEY_HORA_FIN = "hora_fin";

	// Login Table Columns names
	private static final String KEY_ID_SERVICE = "id_service";
	private static final String KEY_NAME_SERV = "nome";
	private static final String KEY_DESCRI = "descri";
	private static final String KEY_TEMPO = "tempo";

	// Login Table Columns names
	private static final String KEY_ID_PC = "id_pc";
	private static final String KEY_NAME_PC = "nome";
	private static final String KEY_MODELO = "modelo";
	private static final String KEY_MARCA = "marca";

	// Login Table Columns names
	private static final String KEY_ID_PC_FK = "id_pc";
	private static final String KEY_ID_PC_OS_FK = "id_pcs_os";

	// Login Table Columns names
	private static final String KEY_ID_SERVICE_FK = " id_service";
	private static final String KEY_ID_SERVICES_OS_FK = "id_services_os";

	// Login Table Columns names
	private static final String KEY_ID_MARCA_AR = "id_marca";
	private static final String KEY_NAME_MARCA_AR = "name_marca";

	// Login Table Columns names
	private static final String KEY_ID_MODELO_AR = " id_modelo";
	private static final String KEY_NOME_MODELO_AR = "name_modelo";

	// Login Table Columns names
	private static final String KEY_ID_TENCAO_AR = "id_tencao_ar";
	private static final String KEY_NOME_TENCAO_AR = "tencao_ar";

	// Login Table Columns names
	private static final String KEY_ID_NV_ECONO = " id_nv_econ";
	private static final String KEY_NOME_NV_ECONO = "nv_economico";

	// Login Table Columns names
	private static final String KEY_ID_CAPACI_TERMI = "id_capa_termi";
	private static final String KEY_NOME_CAPACI_TERMICA = "capaci_termi";


	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_CPF_CNPJ = "cpf_cnpj";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_UPDATE_AT = "update_at";
	private static final String KEY_TIPO_CAD = "tipo_cad";
	private static final String KEY_FONE = "fone1";
	private static final String KEY_CELL = "fone2";
	private static final String KEY_ENDER_CLI = "ender";
	private static final String KEY_BAIRRO = "bairro";
	private static final String KEY_POINT_REF = "point_ref";
	private static final String KEY_CEP_CLI = "cep";

	// GUARDA DADOS ORDEM SERVICE Table Columns names
	private static final String KEY_ID_CLI_CACHE = "id_cli_cache";
	private static final String KEY_TIPO_MANU_CACHE = "tipo_manu_cache";
	private static final String KEY_SERV_REALI_CACHE = "serv_reali_cache";
	private static final String KEY_PECS_PROB_CACHE = "pecs_prob_cache";
	private static final String KEY_OBS_CACHE = "obs_cache";

	//Table nomes Colunas Serviços pendentes
	private static final String KEY_ID_REFRI = "id_refri";
	private static final String KEY_PESO_AR = "peso";
	private static final String KEY_HAS_CONTROL = "has_control";
	private static final String KEY_HAS_EXAUSTOR = "has_exaustor";
	private static final String KEY_SAIDA_DE_AR = "saida_ar";
	private static final String KEY_CAPACIDADE_TERMI = "capaci_termica";
	private static final String KEY_TENSAO_TOMADA = "tencao_tomada";
	private static final String KEY_HAS_TIMER = "has_timer";
	private static final String KEY_TIPO_MODELO_AR = "tipo_modelo";
	private static final String KEY_MARCA_AR = "marca";
	private static final String KEY_TEMP_USO = "temp_uso";
	private static final String KEY_NIVEL_ECON = "nivel_econo";
	private static final String KEY_TAMANHO = "tamanho";
	private static final String KEY_FOTO1 = "foto1";
	private static final String KEY_FOTO2 = "foto2";
	private static final String KEY_FOTO3 = "foto3";
	private static final String KEY_ID_CLIENTE_AR = "id_cliente";

	//Table nomes Colunas Serviços pendentes
	private static final String KEY_ID_SERV_PEN = "id_serv_pen";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_CLIENTE_ID = "cliente_id";
	private static final String KEY_LOTACIONAMENTO = "lotacionamento";
	private static final String KEY_ENDER = "ender";
	private static final String KEY_CEP = "cep";
	private static final String KEY_COMPLEMENTO = "complemento";
	private static final String KEY_DATA_SERV = "data_serv";
	private static final String KEY_HORA_SERV = "hora_serv";
	private static final String KEY_DESCRI_CLI_PROBLEM = "descri_cli_problem";
	private static final String KEY_DESCRI_TECNI_PROBLEM = "descri_tecni_problem";
	private static final String KEY_DESCRI_CLI_REFRIGERA = "descri_cli_refrigera";
	private static final String KEY_STATUS_SERV = "status_serv";
	private static final String KEY_NOME_CLI = "nome";
	private static final String KEY_TIPO_CLI = "tipo";
	private static final String KEY_FONE1 = "fone1";
	private static final String KEY_FONE2 = "fone2";
	private static final String KEY_ID_REFRI_CLI = "id_refri_cli";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	String CREATE_TABLE_MY_SERV_PEN =

			"CREATE TABLE " + TABLE_MY_SERV_PEN + "("
					+ KEY_ID_SERV_PEN + " INTEGER PRIMARY KEY,"
					+ KEY_LATITUDE + " double,"
					+ KEY_LONGITUDE + " double,"
					+ KEY_CLIENTE_ID + " INTEGER,"
					+ KEY_LOTACIONAMENTO + " VARCHAR(25),"
					+ KEY_ENDER + " TEXT,"
					+ KEY_COMPLEMENTO + " TEXT,"
					+ KEY_CEP + " TEXT,"
					+ KEY_DATA_SERV + " VARCHAR(10),"
					+ KEY_HORA_SERV + " VARCHAR(10),"
					+ KEY_DESCRI_CLI_PROBLEM + " TEXT,"
					+ KEY_DESCRI_TECNI_PROBLEM + " TEXT,"
					+ KEY_NOME_CLI + " TEXT,"
					+ KEY_TIPO_CLI + " TEXT,"
					+ KEY_DESCRI_CLI_REFRIGERA + " TEXT,"
					+ KEY_ID_REFRI_CLI + " INT,"
					+ KEY_STATUS_SERV + " TEXT,"
					+ KEY_FONE1 + " TEXT,"
					+ KEY_FONE2 + " TEXT" + ");";

	String CREATE_AR_CLIENTE =

			"CREATE TABLE " + TABLE_AR_CLIENTE + "("
					+ KEY_ID_REFRI + " INTEGER PRIMARY KEY,"
					+ KEY_PESO_AR + " double,"
					+ KEY_HAS_CONTROL + " INT(1),"
					+ KEY_HAS_EXAUSTOR + " INT(1),"
					+ KEY_SAIDA_DE_AR + " INT(1)," //VERIFICAR ESSA DESCRIÇÃO
					+ KEY_CAPACIDADE_TERMI + " INT,"
					+ KEY_TENSAO_TOMADA + " INT,"
					+ KEY_HAS_TIMER + " INT(1),"
					+ KEY_TIPO_MODELO_AR + " INT,"
					+ KEY_MARCA_AR + " INT,"
					+ KEY_TEMP_USO + " DOUBLE,"
					+ KEY_NIVEL_ECON + " INT,"
					+ KEY_TAMANHO + " VARCHAR(10),"
					+ KEY_LOTACIONAMENTO + " VARCHAR(25),"
					+ KEY_FOTO1 + " longblob,"
					+ KEY_FOTO2 + " longblob,"
					+ KEY_FOTO3 + " longblob,"
					+ KEY_ID_CLIENTE_AR + " INT" + ");";

	String CREATE_TABLE_USER_CLI =
			"CREATE TABLE " + TABLE_USER_CLI + "("
					+ KEY_ID + " INTEGER PRIMARY KEY ,"
					+ KEY_NAME + " TEXT,"
					+ KEY_CPF_CNPJ + " INT UNIQUE,"
					+ KEY_EMAIL + " TEXT UNIQUE,"
					+ KEY_UID + " TEXT,"
					+ KEY_TIPO_CAD + " TEXT,"
					+ KEY_FONE + " TEXT,"
					+ KEY_CELL + " TEXT,"
					+ KEY_ENDER_CLI + " TEXT,"
					+ KEY_BAIRRO + " TEXT,"
					+ KEY_POINT_REF+ " TEXT,"
					+ KEY_CEP_CLI+ " INT,"
					+ KEY_UPDATE_AT+ " TEXT,"
					+ KEY_CREATED_AT + " TEXT" + ");";

	String CREATE_TABLE_OS =
			"CREATE TABLE " + TABLE_OS + "("
					+ KEY_ID_OS + " INTEGER NOT NULL PRIMARY KEY,"
					+ KEY_ID_CLI + " INT,"
					+ KEY_MATRI_FUNC + " TEXT,"
					+ KEY_TIPO_MANU + " TEXT NULL,"
					+ KEY_DATA + " TEXT NULL,"
					+ KEY_HORA_INI + " TEXT NULL,"
					+ KEY_HORA_FIN + " TEXT NULL,"
					+ KEY_OBS + " TEXT NULLL" + ");";

	String CREATE_TABLE_SERVICES =
			"CREATE TABLE " + TABLE_SERVICES + "("
					+ KEY_ID_SERVICE + " INT NOT NULL PRIMARY KEY,"
					+ KEY_NAME_SERV + " TEXT,"
					+ KEY_DESCRI + " TEXT,"
					+ KEY_TEMPO + " TEXT" + ");";

	String CREATE_TABLE_PECS =
			"CREATE TABLE " + TABLE_PECS + "("
					+ KEY_ID_PC + " INT NOT NULL PRIMARY KEY,"
					+ KEY_NAME_PC + " TEXT,"
					+ KEY_MODELO + " TEXT,"
					+ KEY_MARCA + " TEXT" + ");";

	String CREATE_PCS_PROB_OS =
			"CREATE TABLE " + TABLE_PCS_PROB_OS + "("
					+ KEY_ID_PC_FK + " INT,"
					+ KEY_ID_PC_OS_FK + " INT" + ");";

	String CREATE_SERV_FUNC_OS =
			"CREATE TABLE " + TABLE_SERV_FUNC + "("
					+ KEY_ID_SERVICE_FK + " INT,"
					+ KEY_ID_SERVICES_OS_FK + " INT" + ");";

	String CREATE_MARCA =
			"CREATE TABLE " + TABLE_MARCA + "("
					+ KEY_ID_MARCA_AR + " INT,"
					+ KEY_NAME_MARCA_AR + " TXT" + ");";

	String CREATE_MODELO =
			"CREATE TABLE " + TABLE_MODELO + "("
					+ KEY_ID_MODELO_AR + " INT,"
					+ KEY_NOME_MODELO_AR + " TXT" + ");";

	String CREATE_TENCAO =
			"CREATE TABLE " + TABLE_TENCAO + "("
					+ KEY_ID_TENCAO_AR + " INT,"
					+ KEY_NOME_TENCAO_AR + " TXT" + ");";

	String CREATE_CAPACI_TERMI =
			"CREATE TABLE " + TABLE_CAPACI_TERMI + "("
					+ KEY_ID_CAPACI_TERMI + " INT,"
					+ KEY_NOME_CAPACI_TERMICA + " TXT" + ");";

	String CREATE_NV_ECON =
			"CREATE TABLE " + TABLE_NV_ECON + "("
					+ KEY_ID_NV_ECONO + " INT,"
					+ KEY_NOME_NV_ECONO + " TXT" + ");";

	String CREATE_GUARDA_DADOS_OS =
			"CREATE TABLE " + TABLE_GUARDA_DADOS_OS + "("
					+ KEY_ID_CLI_CACHE + " INT ,"
					+ KEY_TIPO_MANU_CACHE + " INT(2),"
					+ KEY_SERV_REALI_CACHE + " INT,"
					+ KEY_PECS_PROB_CACHE + " INT,"
					+ KEY_OBS_CACHE + " TEXT" + ");";
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {



		db.execSQL(CREATE_TABLE_MY_SERV_PEN);
		db.execSQL(CREATE_AR_CLIENTE);
		db.execSQL(CREATE_TABLE_USER_CLI);
		db.execSQL(CREATE_TABLE_OS);
		db.execSQL(CREATE_TABLE_SERVICES);
		db.execSQL(CREATE_TABLE_PECS);
		db.execSQL(CREATE_PCS_PROB_OS);
		db.execSQL(CREATE_SERV_FUNC_OS);
		db.execSQL(CREATE_GUARDA_DADOS_OS);
		db.execSQL(CREATE_MARCA);
		db.execSQL(CREATE_MODELO);
		db.execSQL(CREATE_CAPACI_TERMI);
		db.execSQL(CREATE_TENCAO);
		db.execSQL(CREATE_NV_ECON);

		Log.d(TAG, "Database tables created------------- "+db+":  "+db.getPageSize()+db.getSyncedTables());
        System.out.println("Database tables created------------- "+db+":  "+db.getPageSize()+db.getVersion());
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS "	+TABLE_MY_SERV_PEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CLI);
		db.execSQL("DROP TABLE IF EXISTS "	+TABLE_SERV_FUNC);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PCS_PROB_OS);
		db.execSQL("DROP TABLE IF EXISTS "	+TABLE_PECS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AR_CLIENTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUARDA_DADOS_OS);
		db.execSQL("DROP TABLE IF EXISTS "	+TABLE_MARCA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODELO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAPACI_TERMI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TENCAO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NV_ECON);
		// Create tables again
		onCreate(db);
	}


	public void addMyServPen(ServPendenteCtrl objetoServPen, String newStatus) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_ID_SERV_PEN , objetoServPen.getId_serv_pen()); // matricula
		values.put(KEY_LATITUDE , objetoServPen.getLatitude()); // matricula
		values.put(KEY_LONGITUDE , objetoServPen.getLongitude()); // Email
		values.put(KEY_CLIENTE_ID , objetoServPen.getCliente_id()); // UID
		values.put(KEY_LOTACIONAMENTO , objetoServPen.getLotacionamento()); // Created At
		values.put(KEY_ENDER , objetoServPen.getEnder()); // matricula
		values.put(KEY_CEP , objetoServPen.getCep()); // matricula
		values.put(KEY_COMPLEMENTO , objetoServPen.getComplemento()); // matricula
		values.put(KEY_DATA_SERV , objetoServPen.getData_serv()); // Email
		values.put(KEY_HORA_SERV , objetoServPen.getHora_serv()); // UID
		values.put(KEY_DESCRI_CLI_PROBLEM , objetoServPen.getDescri_cli_problem()); // Created At
		values.put(KEY_DESCRI_TECNI_PROBLEM , objetoServPen.getDescri_tecni_problem()); // matricula
		values.put(KEY_DESCRI_CLI_REFRIGERA , objetoServPen.getDescri_cli_refrigera()); // Email
		values.put(KEY_STATUS_SERV , newStatus); // UID
		values.put(KEY_FONE1 , objetoServPen.getFone1()); // Email
		values.put(KEY_FONE2 , objetoServPen.getFone2()); // UID
		values.put(KEY_NOME_CLI , objetoServPen.getNomeCli()); // Email
		values.put(KEY_TIPO_CLI , objetoServPen.getTipoCli()); // UID
		values.put(KEY_ID_REFRI_CLI , objetoServPen.getId_refriCli()); // UID

		// Inserting Row
		long id = db.insert(TABLE_MY_SERV_PEN, null, values);
		System.out.println("saida tabela Serviços Pendentes: "+values);

		db.close(); // Closing database connection

		Log.d(TAG, "New serviço inserted into sqlite: " + id);
	}

    public void addMyServPen(ServPendenteCtrl objetoServPen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID_SERV_PEN , objetoServPen.getId_serv_pen()); // matricula
        values.put(KEY_LATITUDE , objetoServPen.getLatitude()); // matricula
        values.put(KEY_LONGITUDE , objetoServPen.getLongitude()); // Email
        values.put(KEY_CLIENTE_ID , objetoServPen.getCliente_id()); // UID
        values.put(KEY_LOTACIONAMENTO , objetoServPen.getLotacionamento()); // Created At
        values.put(KEY_ENDER , objetoServPen.getEnder()); // matricula
        values.put(KEY_CEP , objetoServPen.getCep()); // matricula
        values.put(KEY_COMPLEMENTO , objetoServPen.getComplemento()); // matricula
        values.put(KEY_DATA_SERV , objetoServPen.getData_serv()); // Email
        values.put(KEY_HORA_SERV , objetoServPen.getHora_serv()); // UID
        values.put(KEY_DESCRI_CLI_PROBLEM , objetoServPen.getDescri_cli_problem()); // Created At
        values.put(KEY_DESCRI_TECNI_PROBLEM , objetoServPen.getDescri_tecni_problem()); // matricula
        values.put(KEY_DESCRI_CLI_REFRIGERA , objetoServPen.getDescri_cli_refrigera()); // Email
        values.put(KEY_STATUS_SERV , objetoServPen.getStatus_serv()); // UID
        values.put(KEY_FONE1 , objetoServPen.getFone1()); // Email
        values.put(KEY_FONE2 , objetoServPen.getFone2()); // UID
        values.put(KEY_NOME_CLI , objetoServPen.getNomeCli()); // Email
		values.put(KEY_TIPO_CLI , objetoServPen.getTipoCli()); // UID
		values.put(KEY_ID_REFRI_CLI , objetoServPen.getId_refriCli()); // UID

        // Inserting Row
        long id = db.insert(TABLE_MY_SERV_PEN, null, values);
        System.out.println("saida tabela Serviços Pendentes: "+values);

        db.close(); // Closing database connection

        Log.d(TAG, "New serviço inserted into sqlite: " + id);
    }

	public void addUser(UserClienteCtrl user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, user.getId()); // Name
		values.put(KEY_NAME, user.getName()); // Name
		values.put(KEY_CPF_CNPJ, user.getCpf_cnpj()); // matricula
		values.put(KEY_EMAIL, user.getEmail()); // Email
		values.put(KEY_UID, user.getUid()); // UID
		values.put(KEY_TIPO_CAD, user.getTipo_cad()); // created_at
		values.put(KEY_FONE, user.getFone1()); // created_at
		values.put(KEY_CELL, user.getFone2()); // created_at
		values.put(KEY_ENDER_CLI, user.getEnder()); // created_at
		values.put(KEY_BAIRRO, user.getBairro()); // created_at
		values.put(KEY_POINT_REF, user.getPoint_ref()); // created_at
		values.put(KEY_CEP_CLI, user.getCep()); // created_at

		// Inserting Row
		long id = db.insert(TABLE_USER_CLI, null, values);
		System.out.println("saida Usuarios: "+values);

		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	public void addOS(int idOS, int id_cli, String matri_func, int tipo_manu, String obs, String data, String hora_ini, String hora_fin) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_OS, idOS); // Name
		values.put(KEY_ID_CLI, id_cli); // Name
		values.put(KEY_MATRI_FUNC, matri_func); // matricula
		values.put(KEY_TIPO_MANU, tipo_manu); // Email
		values.put(KEY_OBS, obs); // UID
		values.put(KEY_DATA, data); // data
		values.put(KEY_HORA_INI, hora_ini); // hora_ini
		values.put(KEY_HORA_FIN, hora_fin); // hora_fin

		// Inserting Row
		long id = db.insert(TABLE_OS, null, values);
		System.out.println("saida de OS: "+values.get(KEY_ID_OS));

		db.close(); // Closing database connection

		Log.d(TAG, "New ordem de service inserted into sqlite: " + id);
	}

	public void addServices(ServicesCtrl service) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_SERVICE, service.getId_service()); // Name
		values.put(KEY_NAME_SERV, service.getNome()); // matricula
		values.put(KEY_DESCRI, service.getDescri()); // Email
		values.put(KEY_TEMPO, service.getTempo()); // UID

		// Inserting Row
		long id = db.insert(TABLE_SERVICES, null, values);
		System.out.println("saida de Service: "+values.get(KEY_ID_SERVICE));

		db.close(); // Closing database connection

		Log.d(TAG, "New service inserted into sqlite: " + id);
	}

	public void addRefrigerador(RefrigeradorCtrl refrigera) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if(!verifyRefri(refrigera.getId_refri())) {
			if (refrigera.getId_refri() == 0) values.put(KEY_ID_REFRI, ""); // Name
			else values.put(KEY_ID_REFRI, refrigera.getId_refri());
			values.put(KEY_PESO_AR, refrigera.getPeso()); // matricula
			values.put(KEY_HAS_CONTROL, refrigera.getHas_control()); // Email
			values.put(KEY_LOTACIONAMENTO, refrigera.getHas_control()); // Email
			values.put(KEY_HAS_EXAUSTOR, refrigera.getHas_exaustor()); // UID
			values.put(KEY_SAIDA_DE_AR, refrigera.getSaida_ar()); // Name
			values.put(KEY_CAPACIDADE_TERMI, refrigera.getCapaci_termica()); // matricula
			values.put(KEY_TENSAO_TOMADA, refrigera.getTencao_tomada()); // Email
			values.put(KEY_HAS_TIMER, refrigera.getHas_timer()); // UID
			values.put(KEY_TIPO_MODELO_AR, refrigera.getTipo_modelo()); // Name
			values.put(KEY_MARCA_AR, refrigera.getMarca()); // matricula
			values.put(KEY_TEMP_USO, refrigera.getTemp_uso()); // Email
			values.put(KEY_NIVEL_ECON, refrigera.getNivel_econo()); // UID
			values.put(KEY_TAMANHO, refrigera.getTamanho()); // Name
			values.put(KEY_FOTO1, refrigera.getFoto1()); // matricula
			values.put(KEY_FOTO2, refrigera.getFoto2()); // Email
			values.put(KEY_FOTO3, refrigera.getFoto3()); // UID
			values.put(KEY_ID_CLIENTE_AR, refrigera.getId_cliente()); // UID
		}else Log.e(TAG, "AR JA CADASTRADO: "+values.get(KEY_ID_REFRI));
		// Inserting Row
		long id = db.insert(TABLE_AR_CLIENTE, null, values);
        Log.e(TAG, "saida de Refrigeradores: "+values.get(KEY_ID_REFRI));

		db.close(); // Closing database connection

        Log.e(TAG, "---New Refrigerador inserted into sqlite: " + id);
        Log.e(TAG, "---New Refrigerador Marca: " +  getNomeMaca(Integer.parseInt(values.get(KEY_MARCA_AR).toString())));
	}
	public boolean verifyRefri(int codRefri){
		UserFuncionarioCtrl user_func = new UserFuncionarioCtrl();
		String selectQuery = "SELECT "+KEY_ID_REFRI+" FROM " + TABLE_AR_CLIENTE+" WHERE "+KEY_ID_REFRI+"="+codRefri;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			return true;
		}
		return false;
	}

	public void addPecs(PecsCtrl pecs) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_PC, pecs.getId_pc()); // Name
		values.put(KEY_NAME_PC, pecs.getNome()); // matricula
		values.put(KEY_MODELO, pecs.getModelo()); // Email
		values.put(KEY_MARCA, pecs.getMarca()); // UID

		// Inserting Row
		long id = db.insert(TABLE_PECS, null, values);
		System.out.println("saida de Peças: "+values.get(KEY_ID_PC));

		db.close(); // Closing database connection

		Log.d(TAG, "New Peça inserted into sqlite: " + id);
	}

	public void addPcsProbleOS(int id_pc, int id_os) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_PC_FK, id_pc); // Name
		values.put(KEY_ID_PC_OS_FK, id_os);

		// Inserting Row
		long id = db.insert(TABLE_PCS_PROB_OS, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_PC_FK));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addServicesFuncOS(int id_service, int id_os) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_SERVICE_FK, id_service); // Name
		values.put(KEY_ID_SERVICES_OS_FK, id_os);

		// Inserting Row
		long id = db.insert(TABLE_SERV_FUNC, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_CLI));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addMarca(int id_marca, String marca) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_MARCA_AR, id_marca); // Name
		values.put(KEY_NAME_MARCA_AR, marca);

		// Inserting Row
		long id = db.insert(TABLE_MARCA, null, values);
		Log.e("Maca ADICIONADA: ", ""+values.get(KEY_ID_MARCA_AR));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de MARCA inserted into sqlite: " + id);
	}

	public void addModelo(int id_modelo, String modelo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_MODELO_AR, id_modelo); // Name
		values.put(KEY_NOME_MODELO_AR, modelo);

		// Inserting Row
		long id = db.insert(TABLE_MODELO, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_MODELO_AR));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addBtu(int id_btu, String btu) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_CAPACI_TERMI, id_btu); // Name
		values.put(KEY_NOME_CAPACI_TERMICA, btu);

		// Inserting Row
		long id = db.insert(TABLE_CAPACI_TERMI, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_CAPACI_TERMI));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addNvEcon(int id_nv_econ, String nv_econ) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_NV_ECONO, id_nv_econ); // Name
		values.put(KEY_NOME_NV_ECONO, nv_econ);

		// Inserting Row
		long id = db.insert(TABLE_NV_ECON, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_NV_ECONO));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addTencaoTomada(int id_tencao, String tencao) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_TENCAO_AR, id_tencao); // Name
		values.put(KEY_NOME_TENCAO_AR, tencao);

		// Inserting Row
		long id = db.insert(TABLE_TENCAO, null, values);
		System.out.println("saida de FK Peças: "+values.get(KEY_ID_TENCAO_AR));

		db.close(); // Closing database connection

		Log.d(TAG, "New FK de Peça inserted into sqlite: " + id);
	}

	public void addDadosOScache(int idCli, int tipoManu, int servReali, int pecsProb, String obs) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_CLI_CACHE, idCli); // Name
		values.put(KEY_TIPO_MANU_CACHE, tipoManu); // matricula
		values.put(KEY_SERV_REALI_CACHE, servReali); // UID
		values.put(KEY_PECS_PROB_CACHE, pecsProb); // Email
		values.put(KEY_OBS_CACHE, obs); // UID

		// Inserting Row
		long id = db.insert(TABLE_GUARDA_DADOS_OS, null, values);
		System.out.println("saida de tabela Dados OS Cache: "+values.get(KEY_ID_CLI_CACHE));

		db.close(); // Closing database connection

		Log.d(TAG, "New Peça inserted into sqlite: " + id);
	}

	public UserClienteCtrl getUserDetails() {
		UserClienteCtrl user = new UserClienteCtrl();
		String selectQuery = "SELECT * FROM " + TABLE_USER_CLI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.setId(cursor.getInt(0));
			user.setName( cursor.getString(1));
			user.setCpf_cnpj(Integer.parseInt(cursor.getString(2)));
			user.setEmail(cursor.getString(3));
			user.setUid(cursor.getString(4));
			user.setTipo_cad(cursor.getString(5));
			user.setFone1(cursor.getString(6));
			user.setFone2(cursor.getString(7));
			user.setEnder(cursor.getString(8));
			user.setBairro(cursor.getString(9));
			user.setPoint_ref(cursor.getString(10));
			user.setCep(Integer.parseInt(cursor.getString(11)));
			user.setCreated_at(cursor.getString(12));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.getName());

		return user;
	}

	public int getLastIdOS() {
		UserFuncionarioCtrl user_func = new UserFuncionarioCtrl();
		String selectQuery = "SELECT * FROM " + TABLE_OS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Move to last row
		cursor.moveToLast();
		int lastOs = cursor.getInt(0);
		cursor.close();

		db.close();
		// return user
		Log.d(TAG, "Ultimo ID_OS:: " +lastOs);

		return lastOs;
	}

	public List<ServPendenteCtrl> getAllMyServPen() {

		List<ServPendenteCtrl> listServPens = new ArrayList<ServPendenteCtrl>();
		String selectQuery = "SELECT * FROM " + TABLE_MY_SERV_PEN ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD MyServPèn: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){
			ServPendenteCtrl servPen = new ServPendenteCtrl();
			servPen.setId_serv_pen(Integer.parseInt(cursor.getString(0)));
			servPen.setLatitude(Double.parseDouble(cursor.getString(1)));
			servPen.setLongitude(Double.parseDouble(cursor.getString(2)));
			servPen.setCliente_id(Integer.parseInt(cursor.getString(3)));
			servPen.setLotacionamento(cursor.getString(4));
			servPen.setEnder(cursor.getString(5));
			servPen.setComplemento(cursor.getString(6));
			servPen.setCep(cursor.getString(7));
			servPen.setData_serv(cursor.getString(8));
			servPen.setHora_serv(cursor.getString(9));
			servPen.setDescri_cli_problem(cursor.getString(10));
			servPen.setDescri_tecni_problem(cursor.getString(11));
			servPen.setNomeCli(cursor.getString(12));
			servPen.setTipoCli(cursor.getString(13));
			servPen.setDescri_cli_refrigera(cursor.getString(14));
			servPen.setId_refriCli(cursor.getInt(15));
			servPen.setStatus_serv(cursor.getString(16));
			servPen.setFone1(cursor.getString(17));
			servPen.setFone2(cursor.getString(18));

			listServPens.add(servPen);

			Log.d(TAG, "Fetching ServPen from Sqlite: " + servPen.toString());

			Log.e("Retorno AllMyServPèn: ","NOME CLI: : >>>>><<<<> "+listServPens.get(cursor.getPosition()).getNomeCli());


		}
		cursor.close();
		db.close();
		// return user
		//Log.d(TAG, "Fetching list MyServPen from Sqlite: " + servPen.toString());

		return listServPens;
	}

	public List<ServicesCtrl> getServices() {

		List<ServicesCtrl> listServ = new ArrayList<ServicesCtrl>();
		String selectQuery = "SELECT * FROM " + TABLE_SERVICES ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD Services: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){
			ServicesCtrl serv = new ServicesCtrl();
			serv.setId_service(Integer.parseInt(cursor.getString(0)));
			serv.setNome(cursor.getString(1));
			serv.setDescri(cursor.getString(2));
			serv.setTempo(cursor.getString(3));

			listServ.add(serv);

			Log.d(TAG, "Fetching ServPen from Sqlite: " + serv.toString());

			Log.e("Retorno AllMyServPèn: ","NOME CLI: : >>>>><<<<> "+listServ.get(cursor.getPosition()).getNome());


		}
		cursor.close();
		db.close();
		// return user
		//Log.d(TAG, "Fetching list MyServPen from Sqlite: " + servPen.toString());

		return listServ;
	}

	public List<PecsCtrl> getPecs() {

		List<PecsCtrl> listPecs = new ArrayList<PecsCtrl>();
		String selectQuery = "SELECT * FROM " + TABLE_PECS ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD Peças: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){
			PecsCtrl pecs = new PecsCtrl();
			pecs.setId_pc(Integer.parseInt(cursor.getString(0)));
			pecs.setNome(cursor.getString(1));
			pecs.setModelo(cursor.getString(2));
			pecs.setMarca(cursor.getString(3));

			listPecs.add(pecs);

			Log.d(TAG, "Fetching Peça from Sqlite: " + pecs.toString());

			Log.e("Retorno Peça: ","NOME PEÇA: : >>>>><<<<> "+listPecs.get(cursor.getPosition()).getNome());


		}
		cursor.close();
		db.close();
		return listPecs;
	}

	public ServPendenteCtrl getMyServiceUidade(int position) {

		ServPendenteCtrl servPen = new ServPendenteCtrl();
		Log.e("Posição ServPEn::: ","--- "+position);
		try {

			String selectQuery = "SELECT  * FROM " + TABLE_MY_SERV_PEN +" WHERE " + KEY_ID_SERV_PEN+" = " + "'"+position+"'";

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			Log.e("Retorno ServPèn: ","Tamanho: >>>>><<<<> "+cursor.getCount());

			// Move to first row
			if(cursor.moveToFirst()) {
				Log.e("Retorno ServPèn: ", "Tamanho: >>>>><<<<> " + cursor.getCount());
				servPen.setId_serv_pen(Integer.parseInt(cursor.getString(0)));
				servPen.setLatitude(Double.parseDouble(cursor.getString(1)));
				servPen.setLongitude(Double.parseDouble(cursor.getString(2)));
				servPen.setCliente_id(Integer.parseInt(cursor.getString(3)));
				servPen.setLotacionamento(cursor.getString(4));
				servPen.setEnder(cursor.getString(5));
				servPen.setComplemento(cursor.getString(6));
				servPen.setCep(cursor.getString(7));
				servPen.setData_serv(cursor.getString(8));
				servPen.setHora_serv(cursor.getString(9));
				servPen.setDescri_cli_problem(cursor.getString(10));
				servPen.setDescri_tecni_problem(cursor.getString(11));
				servPen.setNomeCli(cursor.getString(12));
				servPen.setTipoCli(cursor.getString(13));
				servPen.setDescri_cli_refrigera(cursor.getString(14));
				servPen.setId_refriCli(cursor.getInt(15));
				servPen.setStatus_serv(cursor.getString(16));
				servPen.setFone1(cursor.getString(17));
				servPen.setFone2(cursor.getString(18));

			}else Log.e("Erro"," Retorno Da consulta do serviço por ID esta vasio");
			//Log.e("CEP : ","----- "+cursor.getString(7));

			cursor.close();
			db.close();
			// return user
			return servPen;

		}catch (Exception e){
			Log.e("Erro Consulta: "," erro no retorno "+e);
		}
		return servPen;
	}

	public RefrigeradorCtrl getArCli(int id_ar) {


		RefrigeradorCtrl objetoAr = new RefrigeradorCtrl();
		Log.e("ID cliente do AR::: ","--- "+id_ar);
		try {

			String selectQuery = "SELECT  * FROM " + TABLE_AR_CLIENTE +" WHERE " + KEY_ID_REFRI+" = " + "'"+id_ar+"'";

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			Log.e("Retorno Ar Cliente: ","Tamanho: >>>>><<<<> "+cursor.getCount());

			// Move to first row

			if(cursor.moveToFirst()){

				objetoAr.setId_refri(cursor.getInt(0));
				objetoAr.setPeso(cursor.getInt(1));
				objetoAr.setHas_control(cursor.getInt(2));
				objetoAr.setHas_exaustor(cursor.getInt(3));
				objetoAr.setSaida_ar(cursor.getString(4));
				objetoAr.setCapaci_termica(cursor.getInt(5));
				objetoAr.setTencao_tomada(cursor.getInt(6));
				objetoAr.setHas_timer(cursor.getInt(7));
				objetoAr.setTipo_modelo(cursor.getInt(8));
				objetoAr.setMarca(cursor.getInt(9));
				objetoAr.setTemp_uso(cursor.getInt(10));
				objetoAr.setNivel_econo(cursor.getInt(11));
				objetoAr.setTamanho(cursor.getString(12));
				objetoAr.setLotacionamento(cursor.getString(13));
				objetoAr.setFoto1(cursor.getBlob(14));
				objetoAr.setFoto2(cursor.getBlob(15));
				objetoAr.setFoto3(cursor.getBlob(16));
				objetoAr.setId_cliente(cursor.getInt(17));

			}
			//Log.e("CEP : ","----- "+cursor.getString(7));

			cursor.close();
			db.close();
			// return user
			return objetoAr;

		}catch (Exception e){
			Log.e("Erro Consulta: "," erro no retorno "+e);
			return null;
		}
	}

	public List<RefrigeradorCtrl> getAllArCli() {


		List<RefrigeradorCtrl> listObjetoAr = new ArrayList<RefrigeradorCtrl>();
		try {

			String selectQuery = "SELECT  * FROM " + TABLE_AR_CLIENTE;

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			Log.e("Retorno Ar Cliente: ","Tamanho: >>>>><<<<> "+cursor.getCount());

			// Move to first row

			if(cursor.moveToNext()){
				RefrigeradorCtrl objetoAr = new RefrigeradorCtrl();

				objetoAr.setId_refri(cursor.getInt(0));
				objetoAr.setPeso(cursor.getInt(1));
				objetoAr.setHas_control(cursor.getInt(2));
				objetoAr.setHas_exaustor(cursor.getInt(3));
				objetoAr.setSaida_ar(cursor.getString(4));
				objetoAr.setCapaci_termica(cursor.getInt(5));
				objetoAr.setTencao_tomada(cursor.getInt(6));
				objetoAr.setHas_timer(cursor.getInt(7));
				objetoAr.setTipo_modelo(cursor.getInt(8));
				objetoAr.setMarca(cursor.getInt(9));
				objetoAr.setTemp_uso(cursor.getInt(10));
				objetoAr.setNivel_econo(cursor.getInt(11));
				objetoAr.setTamanho(cursor.getString(12));
				objetoAr.setLotacionamento(cursor.getString(13));
				objetoAr.setFoto1(cursor.getBlob(14));
				objetoAr.setFoto2(cursor.getBlob(15));
				objetoAr.setFoto3(cursor.getBlob(16));
				objetoAr.setId_cliente(cursor.getInt(17));

				listObjetoAr.add(objetoAr);
				Log.e(TAG,"Ar Adicionado: "+objetoAr.toString());
			}else{
				RefrigeradorCtrl objetoAr = new RefrigeradorCtrl();
				listObjetoAr.add(objetoAr);
			}
			cursor.close();
			db.close();
			// return user
			return listObjetoAr;

		}catch (Exception e){
			Log.e("Erro Consulta: "," erro no retorno "+e);
			return listObjetoAr;
		}
	}


	public String[] getDadosOScache(int idCli) {

		String[] dadosCacheOS = {"","","","","",""};
		String selectQuery = "SELECT * FROM " + TABLE_GUARDA_DADOS_OS +" WHERE "+KEY_ID_CLI_CACHE+ " = '"+idCli+"'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD DADOS OS: ",""+cursor.getCount());
		// Move to first row
		if(cursor.moveToFirst()){
			dadosCacheOS[0] = ""+cursor.getString(0);
			dadosCacheOS[1] = ""+(cursor.getString(1));
			dadosCacheOS[2] = ""+(cursor.getString(2));
			dadosCacheOS[3] = ""+(cursor.getString(3));
			dadosCacheOS[4] = ""+(cursor.getString(4));


			Log.d(TAG, "Fetching getDadosOScache from Sqlite: " + dadosCacheOS[0]);

			Log.e("Retorno DADOS CACHE: ","NOME CLI: : >>>>><<<<> "+dadosCacheOS[0]);


		}else return null;
		cursor.close();
		db.close();
		return dadosCacheOS;
	}

    public ArrayList<String> getMacas() {

        ArrayList<String> listMarcas = new ArrayList<String>();
        String selectQuery = "SELECT "+KEY_NAME_MARCA_AR+" FROM " + TABLE_MARCA ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("Tamanho BD Marca: ",""+cursor.getCount());
        // Move to first row
        while(cursor.moveToNext()){

            listMarcas.add(cursor.getString(0));

            Log.d(TAG, "Fetching ServPen from Sqlite: " + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return listMarcas;
    }

    public String getNomeMaca(int id) {

        String marca;
       String selectQuery = "SELECT "+KEY_NAME_MARCA_AR+" FROM " + TABLE_MARCA +" WHERE "+KEY_ID_MARCA_AR+" = "+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("Tamanho BD Marca: ",""+cursor.getCount());
        // Move to first row
        cursor.moveToFirst();

        marca = cursor.getString(0);

            Log.e(TAG, "NOme Marca: " + cursor.getString(0));

        cursor.close();
        db.close();
        return marca;
    }

    public ArrayList<String> getModelo() {

        ArrayList<String> listModelo = new ArrayList<String>();
        String selectQuery = "SELECT "+KEY_NOME_MODELO_AR+" FROM " + TABLE_MODELO ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("Tamanho BD getModelo: ",""+cursor.getCount());
        // Move to first row
        while(cursor.moveToNext()){

            listModelo.add(cursor.getString(0));

            Log.d(TAG, "Fetching ServPen from Sqlite: " + cursor.getString(0));
        }
        cursor.close();
        db.close();
        return listModelo;
    }

	public ArrayList<String> getTencaoTomada() {

        ArrayList<String> listTencaoTomada = new ArrayList<String>();
		String selectQuery = "SELECT "+KEY_NOME_TENCAO_AR+" FROM " + TABLE_TENCAO ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD Tencao: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){

			listTencaoTomada.add(cursor.getString(0));

			Log.d(TAG, "Fetching ServPen from Sqlite: " + cursor.getString(0));
		}
		cursor.close();
		db.close();
		return listTencaoTomada;
	}

	public ArrayList<String> getNvEcon() {

        ArrayList<String> listNvEcon = new ArrayList<String>();
		String selectQuery = "SELECT "+KEY_NOME_NV_ECONO+" FROM " + TABLE_NV_ECON ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD getNvEcon: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){

			listNvEcon.add(cursor.getString(0));

			Log.d(TAG, "Fetching ServPen from Sqlite: " + cursor.getString(0));
		}
		cursor.close();
		db.close();
		return listNvEcon;
	}

	public ArrayList<String> getBTU() {

        ArrayList<String> listBTU = new ArrayList<String>();
		String selectQuery = "SELECT "+KEY_NOME_CAPACI_TERMICA+" FROM " + TABLE_CAPACI_TERMI ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.e("Tamanho BD getBTU: ",""+cursor.getCount());
		// Move to first row
		while(cursor.moveToNext()){

			listBTU.add(cursor.getString(0));

			Log.d(TAG, "Fetching ServPen from Sqlite: " + cursor.getString(0));
		}
		cursor.close();
		db.close();
		return listBTU;
	}

    public String getNomeBTU(int id) {

        String btu;
        String selectQuery = "SELECT "+KEY_NOME_CAPACI_TERMICA+" FROM " + TABLE_CAPACI_TERMI +" WHERE "+KEY_ID_CAPACI_TERMI+" = "+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("Tamanho Btu: ",""+cursor.getCount());
        // Move to first row
        cursor.moveToFirst();

        btu = cursor.getString(0);

        Log.e(TAG, "Nome Btu: " + cursor.getString(0));

        cursor.close();
        db.close();
        return btu;
    }

    public int getIdPecs(String nome) {

        int  idPC;
        String selectQuery = "SELECT "+KEY_ID_PC+" FROM " + TABLE_PECS +" WHERE "+KEY_NAME_PC+" LIKE '"+nome+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("SIZE RETURN getIdPecs: ",""+cursor.getCount());
        // Move to first row
        cursor.moveToFirst();

        idPC = cursor.getInt(0);

        Log.e(TAG, "ID PECS: " + cursor.getString(0));

        cursor.close();
        db.close();
        return idPC;
    }

    public int getIdServices(String nome) {

        int  idService;
        String selectQuery = "SELECT "+KEY_ID_SERVICE+" FROM " + TABLE_SERVICES +" WHERE "+KEY_NAME_SERV+" LIKE '"+nome+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e(TAG,"SIZE RETURN getIdServices: "+cursor.getCount());
        // Move to first row
        cursor.moveToFirst();

        idService = cursor.getInt(0);

        Log.e(TAG, "ID SERVICE: " + cursor.getString(0));

        cursor.close();
        db.close();
        return idService;
    }

	public boolean verifyDataHoraServIfEquals(String data, String hora) {

		try {

			String selectQuery = "SELECT  * FROM " + TABLE_MY_SERV_PEN +" WHERE " + KEY_DATA_SERV+" LIKE '"+data+"' AND "+ KEY_HORA_SERV+" LIKE '"+hora+"'";

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			Log.e("Retorno ServPèn: ","Tamanho: >>>>><<<<> "+cursor.getCount());

			// Move to first row
			if(cursor.moveToFirst()) {
				Log.e("Retorno ServPèn: ", "Tamanho: >>>>><<<<> " + cursor.getCount());
				return true;

			}else{
				Log.e("Erro"," Retorno Da consulta do serviço por ID esta vasio "+cursor.getString(1));
				return false;
			}
			//Log.e("CEP : ","----- "+cursor.getString(7));

			//cursor.close();
			//db.close();


		}catch (Exception e){
			Log.e("Erro Consulta: "," erro no retorno "+e);
		}
		return false;
	}

	public void updateStatusServ(int id_serv_pen, String newStatus){
		ContentValues valores;
		String where;

		SQLiteDatabase db = this.getWritableDatabase();

		where = KEY_ID_SERV_PEN + "=" + id_serv_pen;

		valores = new ContentValues();
		valores.put(KEY_STATUS_SERV, newStatus);

		db.update(TABLE_MY_SERV_PEN,valores,where,null);
		db.close();
	}

	public void updateUserCli(UserClienteCtrl userCli){
		ContentValues valores;
		String where;
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			//where = KEY_ID_SERV_PEN + "=" + id_serv_pen;

			valores = new ContentValues();
			valores.put(KEY_NAME, userCli.getName());
			valores.put(KEY_CPF_CNPJ, userCli.getCpf_cnpj());
			valores.put(KEY_EMAIL, userCli.getEmail());
			valores.put(KEY_TIPO_CAD, userCli.getTipo_cad());
			valores.put(KEY_FONE, userCli.getFone2());
			valores.put(KEY_CELL, userCli.getFone1());
			valores.put(KEY_ENDER_CLI, userCli.getEnder());
			valores.put(KEY_BAIRRO, userCli.getBairro());
			valores.put(KEY_POINT_REF, userCli.getPoint_ref());
			valores.put(KEY_CEP_CLI, userCli.getCep());

			db.update(TABLE_USER_CLI, valores, null, null);
			db.close();
		}catch (Exception e){
			Log.e(TAG," Erro ao atualizar: "+e);
		}
	}

	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER_CLI, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteServices() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_SERVICES, null, null);
		//db.execSQL(CREATE_TABLE_SERVICES);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deletePecs() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_PECS, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteMarcas() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_MARCA, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteModelos() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_MODELO, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteTencao() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_TENCAO, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteNvEcon() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_NV_ECON, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteBTU() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_CAPACI_TERMI, null, null);
		//db.execSQL(CREATE_TABLE_PECS);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteReifigera() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_AR_CLIENTE, null, null);
		//db.execSQL(TABLE_AR_CLIENTE);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteMyServPen(int idServPen) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_MY_SERV_PEN,  KEY_ID_SERV_PEN + " = " + idServPen, null);
		db.close();

		Log.e(TAG, "Deleted o Serviço Pendentes info from sqlite");
	}

	public void deleteAllMyServPen() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_MY_SERV_PEN, null, null);
		db.close();

		Log.d(TAG, "Deleted all os Serviços Pendentes info from sqlite");
	}

	public void deleteDadosOScahe(int idCli) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			// Delete All Rows
			db.delete(TABLE_GUARDA_DADOS_OS, KEY_ID_CLI_CACHE + " = " + idCli, null);
		}catch (Exception ex){
			Log.e("Retorno query Delete ",""+ex);
		}
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void deleteUniRefrigera(int idrRefri) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			// Delete All Rows
			db.delete(TABLE_AR_CLIENTE, KEY_ID_REFRI + " = " + idrRefri, null);
		}catch (Exception ex){
			Log.e("Erro ao Deletar Ar ",""+ex);
		}
		db.close();

		Log.e(TAG, "Ar Deletado Com Sucesso!!!");
	}
}
