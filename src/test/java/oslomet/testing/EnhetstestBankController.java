package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn(){
        //arrage
        List<Transaksjon> transaksjoner= new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(12, "12345678901", 100.00,
                "2003-04-04", "Gratulerer med dagen", "vet ikke hva dette er, men dgb",
                "09876543210");

        Transaksjon transaksjon2 = new Transaksjon(15, "12345678901", 13000.00,
                "2009-09-01", "Betaling av motorsykkel", "vet ikke hva dette er, men dgb",
                "25384638247");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto enKonto = new Konto("23456789012", "12345678901",
                20000.00, "Sparekonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("23456789012");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(enKonto);

        //act
        Konto testKonto = bankController.hentTransaksjoner("12345678901", "", "");
        List<Transaksjon> resultat = testKonto.getTransaksjoner();

        //assert
        assertEquals(resultat, transaksjoner);
    }

    @Test
    public void henttransaksjoner_IkkeLoggetInn(){
        //arange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner("12345678901", "", "");

        //assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldo_LoggetInn(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        List<Konto> kontoliste = new ArrayList<>();

        Konto konto1 = new Konto("12345678901", "23456789012",
                3782.18, "Brukskonto", "NOK", transaksjoner);

        Konto konto2 = new Konto("12345678901", "23456789067",
                201300.12, "Spar", "NOK", transaksjoner);

        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentSaldi("12345678901")).thenReturn(kontoliste);

        //act
        List<Konto> resultat = bankController.hentSaldi();

        //assert
        assertEquals(kontoliste, resultat);
    }

    @Test
    public void hentSaldo_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = bankController.hentSaldi();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn(){
        //arange
        Transaksjon enTransaksjon = new Transaksjon(1783, "56789012345", 450.00,
                "2018-12-23", "God jul", "person som skal få?", "34567890123");

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        //act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        //arrange
        Transaksjon enTransaksjon = new Transaksjon(1783, "56789012345", 450.00,
                "2018-12-23", "God jul", "person som skal få?", "34567890123");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        //assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn(){
        //arrange
        List<Transaksjon> transaksjoner= new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(12, "12345678901", 100.00,
                "2003-04-04", "Gratulerer med dagen", "vet ikke hva dette er, men dgb",
                "09876543210");

        Transaksjon transaksjon2 = new Transaksjon(15, "12345678901", 13000.00,
                "2009-09-01", "Betaling av motorsykkel", "vet ikke hva dette er, men dgb2",
                "25384638247");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentBetaling_IkkeloggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //assert
        assertNull(resultat);
    }

    @Test
    public void utforbetaling_LoggetInn(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon entransaksjon = new Transaksjon(369, "34567890123", 321.90,
                "2018-05-16", "Gratulerer med dagen", "hva er avventer?", "78901234567");

        transaksjoner.add(entransaksjon);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(369);

        //assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon entransaksjon = new Transaksjon(369, "34567890123", 321.90,
                "2018-05-16", "Gratulerer med dagen", "hva er avventer?", "78901234567");

        transaksjoner.add(entransaksjon);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(369);

        //assert
        assertNull(resultat);
    }

    @Test
    public void endre_LoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("67890123456", "Kari", "Olavsen",
                "Stavangerbrygga 84", "7253", "Stavanger", "90909090", "HeiHei" );

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = bankController.endre(enKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("67890123456", "Kari", "Olavsen",
                "Stavangerbrygga 84", "7253", "Stavanger", "90909090", "HeiHei" );
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(enKunde);

        //assert
        assertNull(resultat);
    }
}

