package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
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

public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_LoggetInn(){
        //arrange
        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto konto1 = new Konto("2345678901", "67676767676", 3600.00, "Brukskonto", "NOK", transaksjoner);
        Konto konto2 = new Konto("2345678904", "67676764545", 36000.00, "Sparekonto", "NOK", transaksjoner);

        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentAlleKonti()).thenReturn(kontoliste);

        //act
        List<Konto> restultat = adminController.hentAlleKonti();

        //assert
        assertEquals(kontoliste, restultat);
    }

    @Test
    public void hentAlleKonti_IkkeLoggetInn(){
        //arrange
        List<Konto> kontoliste = new ArrayList<>();
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Konto konto1 = new Konto("2345678901", "67676767676", 3600.00, "Brukskonto", "NOK", transaksjoner);
        Konto konto2 = new Konto("2345678904", "67676764545", 36000.00, "Sparekonto", "NOK", transaksjoner);

        kontoliste.add(konto1);
        kontoliste.add(konto2);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> restultat = adminController.hentAlleKonti();

        //assert
        assertNull(restultat);
    }

    @Test
    public void registrerKonto_LoggetInn(){
        //arrange
        List<Transaksjon> transakjsoner = new ArrayList<>();
        Konto enKonto = new Konto("56789012345", "12121212121", 200.00,
                "Gavekort", "NOK", transakjsoner);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminController.registrerKonto(enKonto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_IkkeLoggetInn(){
        //arrange
        List<Transaksjon> transakjsoner = new ArrayList<>();
        Konto enKonto = new Konto("56789012345", "12121212121", 200.00,
                "Gavekort", "NOK", transakjsoner);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminController.registrerKonto(enKonto);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_LoggetInn(){
        //arrange
        List<Transaksjon> transakjsoner = new ArrayList<>();
        Konto enKonto = new Konto("56789012345", "12121212121", 200.00,
                "Gavekort", "NOK", transakjsoner);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminController.endreKonto(enKonto);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_IkkeLogetInn(){
        //arrange
        List<Transaksjon> transakjsoner = new ArrayList<>();
        Konto enKonto = new Konto("56789012345", "12121212121", 200.00,
                "Gavekort", "NOK", transakjsoner);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminController.endreKonto(enKonto);

        //assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_LoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.slettKonto(anyString())).thenReturn("OK");

        //act
        String resultat = adminController.slettKonto("45678901234");

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminController.slettKonto("45678901234");

        //assert
        assertEquals("Ikke innlogget", resultat);
    }
}
