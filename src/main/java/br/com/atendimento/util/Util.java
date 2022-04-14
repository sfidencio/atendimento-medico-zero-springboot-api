package br.com.atendimento.util;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.inwords.FormatoDeInteiro;
import br.com.caelum.stella.inwords.FormatoDeReal;
import br.com.caelum.stella.inwords.NumericToWordsConverter;
import br.com.caelum.stella.tinytype.CNPJ;
import br.com.caelum.stella.tinytype.CPF;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Util {



    public static String iniciaisMaiusculas(String str) {
        String words[] = str.split("\\s");
        String capitalizeStr = "";

        for (String word : words) {
            // Capitalize first letter
            String firstLetter = word.substring(0, 1);
            // Get remaining letter
            String remainingLetters = word.substring(1);
            capitalizeStr += firstLetter.toUpperCase() + remainingLetters + " ";
        }
        return capitalizeStr;
    }

    public static String colocarZerosEsquerdaStringBarCode(String num) {
        String str2 = String.format("%13s", num).replace(' ', '0');
        //System.out.println(str2);
        //return String.format("%013s", num); // retorna 000005
        return str2;
    }

    public static String colocarZerosEsquerdaString2Caracteres(String num) {
        String str2 = String.format("%2s", num).replace(' ', '0');
        //System.out.println(str2);
        //return String.format("%013s", num); // retorna 000005
        return str2;
    }

    public static String formatarDataEnvioAPI(LocalDate data) throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        return formatador.format(data);
    }

    public static LocalDate formatarDataGravarBancoDados(String data) throws ParseException {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data,formatador);
    }

    public static String colocarZerosEsquerdaStringBarCode(Long num) {
        return String.format("%013d", num); // retorna 000005
    }

    public static Boolean isNumero(String str) {
        return StringUtils.isNumeric(str);
    }

    public static String formatarNumeroDecimal2CasasCifrao(Double num) {
        Locale ptBr = new Locale("pt", "BR");
        //NumberFormat formatar = new DecimalFormat("#0.00");
        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(num);
        return valorString;
    }

    public static String formatarNumeroDecimal2Casas(Double num) {
        NumberFormat formatar = new DecimalFormat("#0.00");
        return formatar.format(num);
    }

    public static String formatarNumeroDecimal3Casas(Double num) {
        NumberFormat formatar = new DecimalFormat("#0.000");
        return formatar.format(num);
    }

    public static String trocarVirgulaPorPonto(String str) {
        return str.trim().replace(",", ".");
    }

    public static String trocarPontoPorVirgula(String str) {
        return str.trim().replace(".", ",");
    }

    public static String removerRCrifrao(String str) {
        return str.trim().replace("R$", "").replace(" ", "");
    }

    public static String removerEspacoBranco(String str) {
        return str.trim().replace(" ", "").replace("  ", "");
    }


    public static String numeroPorExtenso(Double numero, TipoFormatoExtenso tipoFormato) {
        NumericToWordsConverter converter = null;
        if (tipoFormato == TipoFormatoExtenso.INTEIRO) {
            converter = new NumericToWordsConverter(new FormatoDeInteiro());
        } else if (tipoFormato == TipoFormatoExtenso.REAL) {
            converter = new NumericToWordsConverter(new FormatoDeReal());
        }
        return converter.toWords(numero);
    }



    public static Boolean isValidoBarCodeEAN8e13(String barCode) {
        int digit;
        int calculated;
        String ean;
        String checkSum = "131313131313";
        int sum = 0;

        //Se nao for numero nao valida
        if (!Util.isNumero(barCode)) {
            return false;
        }

        if (barCode.length() == 8 || barCode.length() == 13) {
            digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1));
            ean = barCode.substring(0, barCode.length() - 1);
            for (int i = 0; i <= ean.length() - 1; i++) {
                sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
            }
            calculated = 10 - (sum % 10);
            return (digit == calculated);
        } else {
            return false;
        }
    }


    public static String formatarCNPJ(String msg) {
        CNPJFormatter f = new CNPJFormatter();
        if (f.canBeFormatted(msg)) {
            return f.format(msg);
        }
        return null;
    }

    public static String formatarCPF(String msg) {
        CPFFormatter f = new CPFFormatter();
        if (f.canBeFormatted(msg)) {
            return f.format(msg);
        }
        return null;
    }

    public static Boolean isCNPJValido(String msg) {
        CNPJ f = new CNPJ(msg);
        if (f.isValid()) {
            return true;
        }
        return false;
    }

    public static Boolean isCPFValido(String msg) {
        CPF f = new CPF(msg);
        if (f.isValido()) {
            return true;
        }
        return false;
    }


    public static String[] separarStringDelimitadorPipeLine(String str) {
        return str.split("\\|");
    }

    public static String[] separarStringDelimitadorHifen(String str) {
        return str.split("\\-");
    }

    public static String[] separarStringDelimitadorVirgula(String str) {
        return str.split("\\,");
    }

    public static String[] separarStringDelimitadorPontoVirgula(String str) {
        return str.split("\\;");
    }



    public static String removerPontosCNPJ(String cnpj) {
        return cnpj.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("/", "");

    }

    public static String getMd5(String texto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(texto.getBytes());
        //Get the hash's bytes
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        return sb.toString();

    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static Double converterParaDouble(String str) throws Exception, NumberFormatException {
        if (str.equals("null") || str.contains("null") || str.trim().length() == 0) {
            return 0.0;
        }
        return Double.parseDouble(str);
    }

    public static String converterParaString(String str) throws Exception, NumberFormatException {
        if (str.equals("null") || str.contains("null")) {
            return "".trim();
        }
        return str.trim();
    }

    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static String removeAcentos2(String string) {
        if (string != null) {
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            string = string.replaceAll("[^\\p{ASCII}]", "");
        }
        return string;
    }

    public static String removeAcentos(String string) {
        string = string.replaceAll("[ÂÀÁÄÃ]", "A");
        string = string.replaceAll("[âãàáä]", "a");
        string = string.replaceAll("[ÊÈÉË]", "E");
        string = string.replaceAll("[êèéë]", "e");
        string = string.replaceAll("ÎÍÌÏ", "I");
        string = string.replaceAll("îíìï", "i");
        string = string.replaceAll("[ÔÕÒÓÖ]", "O");
        string = string.replaceAll("[ôõòóö]", "o");
        string = string.replaceAll("[ÛÙÚÜ]", "U");
        string = string.replaceAll("[ûúùü]", "u");
        string = string.replaceAll("Ç", "C");
        string = string.replaceAll("ç", "c");
        string = string.replaceAll("[ýÿ]", "y");
        string = string.replaceAll("Ý", "Y");
        string = string.replaceAll("ñ", "n");
        string = string.replaceAll("Ñ", "N");
        return string;
    }

    /*
     *Zero a esquerda
     */
    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    /*
     *Zero a direita
     */
    public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

    public static int obterMesAtual() {
        /*Date date = new Date(System.currentTimeMillis()); 
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);*/
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }

    public static int obterDiaAtual() {
        /*Date date = new Date(System.currentTimeMillis()); 
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);*/
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int day = localDate.getDayOfMonth();
        return day;
    }

    public static int obterAnoAtual() {
        /*Date date = new Date(System.currentTimeMillis()); 
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);*/
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        return year;
    }

    public static Date obterDataHoraTimeZone() {
        return Calendar.getInstance().getTime();
    }

    public static String formatarNumeroDecimal2CasasV2(BigDecimal num) {
        NumberFormat formatar = new DecimalFormat("#,##0.00");
        return formatar.format(num);
    }

    //Preco
    public static String formatarNumeroDecimal2CasasCifrao(BigDecimal num) {
        Locale ptBr = new Locale("pt", "BR");
        //NumberFormat formatar = new DecimalFormat("#0.00");
        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(num);
        return valorString;
    }

    //Estoque
    public static String formatarNumeroDecimal3CasasV3(BigDecimal num) {
        //https://www.devmedia.com.br/formatando-valores-numericos-em-java/36811
        Locale ptBr = new Locale("pt", "BR");
        //NumberFormat inteiro = NumberFormat.getInstance();
        //NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
        //NumberFormat percentual = NumberFormat.getPercentInstance(localeBR);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(ptBr);
        return numberFormat.format(num);
    }

    public static String formatarMoedaDecimal2Casas(BigDecimal num) {
        return NumberFormat.getCurrencyInstance().format(num);
    }

}
