module com.lolrewqsda.zahlenraten.zahlenraten {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.lolrewqsda.zahlenraten to javafx.fxml;
    exports com.lolrewqsda.zahlenraten;
}