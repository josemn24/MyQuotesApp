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

    public interface OnItemClickListener {
        void onItemClick(Quotation quotation);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private List<Quotation> listQuotes;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

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

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnLongItemClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setQuotations(List<Quotation> list){
        this.listQuotes = list;
        this.notify();
    }

    @Override
    public int getItemCount() {
        return listQuotes.size();
    }

    public Quotation getItem(int position) {
        return listQuotes.get(position);
    }

    public void removeItem(int position) {
        listQuotes.remove(position);
    }

    public void removeQuotation(int position) {
        removeItem(position);
        this.notifyItemRemoved(position);
    }

    public void removeAllQuotation() {
        int length = listQuotes.size();
        listQuotes.clear();
        this.notifyItemRangeRemoved(0, length);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvQuote;
        public TextView tvAuthor;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            tvQuote = view.findViewById(R.id.txtQuote);
            tvAuthor = view.findViewById(R.id.txtAuthor);
        }

        @Override
        public void onClick(View v) {

            clickListener.onItemClick(getItem(getAdapterPosition()));

        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }

    public List<Quotation> getListQuotes() {
        return listQuotes;
    }
}
