package es.upv.dadm.myquotesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.pojo.Quotation;

public class FavouriteQuotesAdapter extends RecyclerView.Adapter<FavouriteQuotesAdapter.ViewHolder> {

    private List<Quotation> listQuotes;

    public FavouriteQuotesAdapter(List<Quotation> list) {
        this.listQuotes = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_item, parent, false);
        FavouriteQuotesAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteQuotesAdapter.ViewHolder holder, int position) {
        holder.tvQuote.setText(listQuotes.get(position).getQuoteText());
        holder.tvAuthor.setText(listQuotes.get(position).getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return listQuotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuote;
        public TextView tvAuthor;

        public ViewHolder(View view) {
            super(view);
            tvQuote = view.findViewById(R.id.txtQuote);
            tvAuthor = view.findViewById(R.id.txtAuthor);
        }
    }

}
