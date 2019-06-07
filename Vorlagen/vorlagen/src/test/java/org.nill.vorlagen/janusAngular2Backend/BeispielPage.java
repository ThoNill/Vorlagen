package janusAngular2Backend;

import janusAngular2Backend.basis.BatchTag;
import janusAngular2Backend.basis.BeanTag;
import janusAngular2Backend.basis.CallTag;
import janusAngular2Backend.basis.PageBasis;
import janusAngular2Backend.basis.SetTag;
import janusAngular2Backend.basis.SqlTag;

public class BeispielPage extends PageBasis {

    @Override
	public void init() {

        add(new SqlTag(
                this,
                "getWerte",
                "         SELECT a.Werteid          FROM Werte a          WHERE gebucht is null          AND storniert is null         and erstellungsdatum >= date(?bisDatum?)         "));
        add(new BeanTag(this, "einbuchenWerte", beans.WerteBuchungsWorker.class)
                .add(new CallTag(this, "buchen", "buchen")
                        .add(new SetTag(this, "werteId", "getWerte.Werteid"))
                        .add(new SetTag(this, "monat", "model_MONAT"))
                        .add(new SetTag(this, "jahr", "model_JAHR"))
                        .add(new SetTag(this, "userID", "model_USERID"))));

        add(new BeanTag(this, "gotoPage", beans.TestCall.class)
                .add(new CallTag(this, "moveZurueck", "moveZurueck")));
        add(new BatchTag(this, "WerteBuchen")
                .add(new SqlTag(
                        this,
                        "getWerte",
                        "         SELECT a.Werteid          FROM Werte a          WHERE gebucht is null          AND storniert is null         and erstellungsdatum >= date(?bisDatum?)         "))
                .getRun()
                .add(new BeanTag(this, "einbuchenWerte",
                        beans.WerteBuchungsWorker.class).add(new CallTag(this,
                        "buchen", "buchen")
                        .add(new SetTag(this, "werteId", "getWerte.Werteid"))
                        .add(new SetTag(this, "monat", "model_MONAT"))
                        .add(new SetTag(this, "jahr", "model_JAHR"))
                        .add(new SetTag(this, "userID", "model_USERID")))));

    }

}