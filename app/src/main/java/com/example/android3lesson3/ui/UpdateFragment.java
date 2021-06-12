package com.example.android3lesson3.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android3lesson3.R;
import com.example.android3lesson3.databinding.FragmentUpdateBinding;
import com.example.android3lesson3.model.PostModel;
import com.example.android3lesson3.network.PostRepository;
import com.example.android3lesson3.ui.main.MainFragment;

import org.jetbrains.annotations.NotNull;

public class UpdateFragment extends Fragment {

    protected FragmentUpdateBinding binding;
    protected PostModel model;
    protected String title, content;
    protected Integer group, user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUpdateBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            model = (PostModel) getArguments().getSerializable(MainFragment.MODEL);
            if (model != null) {
                showModel(model);
            }
        }
        setupListener();
    }

    private void showModel(PostModel model) {
        binding.editTitleItemPost.setText(model.getTitle());
        binding.editContentItemPost.setText(model.getContent());
        binding.editGroupItemPost.setText(String.valueOf(model.getGroup()));
        binding.editUserItemPost.setText(String.valueOf(model.getUser()));
    }

    private void setupListener() {
        binding.btnSaveItemPost.setOnClickListener(v -> {
            collectTexts();
            updatePost();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
        });
    }

    private void collectTexts() {
        title = binding.editTitleItemPost.getText().toString();
        content = binding.editContentItemPost.getText().toString();
        user = Integer.valueOf(binding.editUserItemPost.getText().toString());
        group = Integer.valueOf(binding.editGroupItemPost.getText().toString());
        setPost();
    }

    private void setPost() {
        model.setContent(content);
        model.setGroup(group);
        model.setTitle(title);
        model.setUser(user);
    }

    private void updatePost() {
        PostRepository.updatePost(model.getId(), model);
    }
}