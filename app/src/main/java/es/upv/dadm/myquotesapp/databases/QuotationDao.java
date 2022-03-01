package es.upv.dadm.myquotesapp.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.upv.dadm.myquotesapp.pojo.Quotation;

@Dao
public interface QuotationDao {

    @Query("SELECT * FROM quotation_table")
    List<Quotation> getQuotations();

    @Query("SELECT * FROM quotation_table WHERE quoteText = :name")
    Quotation getQuotation(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);

    @Query("DELETE FROM quotation_table")
    void deleteAllQuotations();

}
