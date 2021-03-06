/*
 * Copyright (C) 2017 XJSHQ@github.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package com.artemchep.horario.ui.fragments.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialcab.MaterialCab;
import com.artemchep.basic.ui.activities.ActivityBase;
import com.artemchep.horario.R;
import com.artemchep.horario._new.activities.SubjectActivity;
import com.artemchep.horario.database.Db;
import com.artemchep.horario.models.Subject;
import com.artemchep.horario.ui.DialogHelper;
import com.artemchep.horario.ui.adapters.SubjectsAdapter;
import com.artemchep.horario.ui.widgets.CustomAppBar;

import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

/**
 * @author Artem Chepurnoy
 */
public class SubjectsFragment extends ModelFragment<Subject> {

    private static final String TAG = "SubjectsFragment";

    @NonNull
    public static Comparator<Subject> createComparator() {
        // sort by name and key
        return new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                int i = o1.name.compareToIgnoreCase(o2.name);
                return i != 0 ? i : o1.key.compareTo(o2.key);
            }
        };
    }

    @NonNull
    @Override
    protected Comparator<Subject> onCreateComparator() {
        return createComparator();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        CustomAppBar appBar = getMainActivity().mAppBar;
        appBar.setTitle(getString(R.string.nav_subjects));
    }

    @Override
    protected void setupFab() {
        super.setupFab();
        FloatingActionButton fab = getMainActivity().mFab;
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase activity = getMainActivity();
                DialogHelper.showSubjectDialog(activity, mTimetablePath, null);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_subjects, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull Subject item) {
        super.onItemClick(view, item);
        /*
        Bundle args = new Bundle();
        args.putBoolean(ModelFragment.EXTRA_EDITABLE, mEditable);
        args.putString(ModelFragment.EXTRA_PATH, mTimetablePath);
        args.putParcelable(MooDetailsFragment.EXTRA_MODEL, item);
        Fragment fragment = new SubjectFragment();
        fragment.setArguments(args);

        MainActivity activity = (MainActivity) getActivity();
        activity.navigateDetailsFrame(fragment);
        */
        Intent intent = new Intent(getContext(), SubjectActivity.class);
//        intent.putExtra(SubjectActivity.EXTRA_PATH, getPath());
        intent.putExtra(SubjectActivity.EXTRA_SUBJECT, item);
        startActivity(intent);
    }

    @NonNull
    @Override
    protected BaseAdapter<Subject> onCreateAdapter() {
        return new SubjectsAdapter(this, mAggregator.getModels());
    }

    @NonNull
    @Override
    protected String getSnackBarRemoveMessage(List<Subject> list) {
        if (list.size() == 1) {
            Subject subject = list.get(0);
            return getString(R.string.snackbar_subject_removed_named, subject.name);
        } else return getString(R.string.snackbar_subject_removed_plural);
    }

    @NonNull
    @Override
    protected Class<Subject> getType() {
        return Subject.class;
    }

    @NonNull
    @Override
    protected String getPath() {
        return mTimetablePath + "/" + Db.SUBJECTS;
    }

    // --------------------------
    // -- ACTION MODE -----------
    // --------------------------

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        super.onCabCreated(cab, menu);
        getActivity().getMenuInflater().inflate(R.menu.master_context_subjects, menu);
        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem menuItem) {
        if (super.onCabItemClicked(menuItem)) {
            return true;
        }

        switch (menuItem.getItemId()) {
            case R.id.action_palette:
                // Build items list
                final List<Subject> list = getSelections();
                if (list.isEmpty()) {
                    Timber.tag(TAG).d("Palette action clicked with no real selections.");
                } else {
                    // get color
                    Integer c = list.get(0).color;
                    int color = list.get(0).color;
                    for (Subject subject : list) {
                        if (color != subject.color) {
                            c = null;
                            break;
                        }
                    }

                    // get ids
                    String[] ids = new String[list.size()];
                    for (int i = 0; i < ids.length; i++) {
                        ids[i] = list.get(i).key;
                    }

                    ActivityBase activity = (ActivityBase) getActivity();
                    DialogHelper.showPaletteDialog(activity, mTimetablePath, ids, c);
                }

                break;
            default:
                return false;
        }

        getMainActivity().finishContextualActionBar();
        return true;
    }

}
