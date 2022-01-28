package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
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

public class EnhetstestAdminKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController adminController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_LoggetInn(){
        //arrange
        List<Kunde> kundeliste = new ArrayList<>();

        Kunde kunde1 = new Kunde("78901234567", "Jens", "Pettersen",
                "Drammensveien 22", "4444", "Drammen", "60606060", "Hemmelig");

        Kunde kunde2 = new Kunde("78901234567", "Lina", "Jonsen",
                "Osloveien 22", "4344", "Oslo", "60606067", "Hyssj12");

        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.hentAlleKunder()).thenReturn(kundeliste);

        //act
        List<Kunde> resultat = adminController.hentAlle();

        //assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void hentAlle_IkkeInnlogget(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Kunde> resultat = adminController.hentAlle();

        //assert
        assertNull(resultat);
    }

    @Test
    public void lagreKunde_LoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("78901234567", "Jens", "Pettersen",
                "Drammensveien 22", "4444", "Drammen", "60606060", "Hemmelig");

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = adminController.lagreKunde(enKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("78901234567", "Jens", "Pettersen",
                "Drammensveien 22", "4444", "Drammen", "60606060", "Hemmelig");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminController.lagreKunde(enKunde);

        //assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endreKunde_LoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("78901234567", "Jens", "Pettersen",
                "Drammensveien 22", "4444", "Drammen", "60606060", "Hemmelig");

        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = adminController.endre(enKunde);

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKunde_IkkeLoggetInn(){
        //arrange
        Kunde enKunde = new Kunde("78901234567", "Jens", "Pettersen",
                "Drammensveien 22", "4444", "Drammen", "60606060", "Hemmelig");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminController.endre(enKunde);

        //assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_LoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(repository.slettKunde(anyString())).thenReturn("OK");

        //act
        String resultat = adminController.slett("41235678903");

        //assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKunde_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminController.slett("41235678903");

        //assert
        assertEquals("Ikke logget inn", resultat);
    }
}
