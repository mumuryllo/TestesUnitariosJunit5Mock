package domain;

import domain.Calculadora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {

    private Calculadora calc;

    @BeforeEach
    void setUp() {
        calc = new Calculadora();
    }

    @Test
    public void testeSoma() {
       int calculo = calc.soma(2,3);

       assertTrue(calculo == 5);
       assertEquals(5,calculo);
    }

    @Test
    public void assertivas(){
        //Equals e True
        assertEquals("Casa","Casa");
        assertNotEquals("casa","Casa");
        assertTrue("casa".equalsIgnoreCase("CASA"));

        //Objetos
        List<String> s1 = new ArrayList<>();
        List<String> s2 = new ArrayList<>();
        List<String> s3 = null;

        assertEquals(s1,s2);
        assertNotSame(s1,s2);
        assertNull(s3);
    }

    @Test
    public void deveRetonarNumeroInteiroNaDivisao(){
       float resultado= calc.dividir(6,3);
       assertEquals(2,resultado);
    }

    @Test
    public void deveRetonarNumeroNegativoNaDivisao(){
        float resultado= calc.dividir(6,-2);
        assertEquals(-3,resultado);
    }


    @Test
    public void deveRetonarNumeroZeroComNumeradorZeroNaDivisao(){
        float resultado= calc.dividir(0,2);
        assertEquals(0,resultado);
    }

    @Test
    public void naoDeveRetonarNumeroComDenominadorZeroNaDivisao(){
        try {
            float resultado= 10/0;
        }catch (ArithmeticException e){
            assertEquals("/ by zero",e.getMessage());
        }
    }

    @Test
    public void naoDeveRetonarNumeroComDenominadorZeroNaDivisaoV2(){
            ArithmeticException exception = assertThrows(ArithmeticException.class,()->{
                float resultado= 10/0;
            });
            assertEquals("/ by zero",exception.getMessage());
    }



    @Test
    public void deveRetonarNumeroDecimalNaDivisao(){
        float resultado= calc.dividir(10,3);
        assertEquals(3.33,resultado,0.01);
    }

    // Testes parametrizaveis
    @ParameterizedTest
    @CsvSource(value = {
            "6,2,3",
            "6,-2,-3",
            "0,2,0"
    })
    void  deveDividirCorretamente(int num, int den, double res){
        float resultado = calc.dividir(num,den);
        assertEquals(res,resultado);
    }

}