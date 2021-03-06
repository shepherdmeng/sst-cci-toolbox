/**
 * Created by sabine.embacher@brockmann-consult.de on 21.07.2016.
 */

var CAR_Tool = function() {

    var _ = this;

    var _class_hidden = 'class_hidden';
    var _images = {};
    var _ui_common = {};
    _ui_common = new CAR_COMMON_UI();

    // ############################
    // ##  General HTML Elements ##
    // ############################

    var _$footer_div = $('#footer-div');
    var _$generalForm = $('#general_form');

    // #############################
    // ##  Session HTML Elements  ##
    // #############################

    var _$loadSession_Button = $('#load_session_button');
    var _$loadSession_Dialog = $('#load_session_form_div');
    var _$loadSession_Form = $('#load_session_form');
    var _$loadSession_FormDropDown = $('#load_session_form_drop_down');
    var _$loadSession_FormButton = $('#load_session_form_button');
    var _$loadSession_FormCloseButton = $('#load_session_form_close_button');

    var _$saveSession_Button = $('#save_session_button');

    var _$saveSessionAs_Button = $('#save_session_as_button');
    var _$saveSessionAs_Dialog = $('#save_session_as_div');
    var _$saveSessionAs_Form = $('#save_session_as_form');
    var _$saveSessionAs_FormNameInput = $('#save_session_as_form_name_input');
    var _$saveSessionAs_FormButton = $('#save_session_as_form_button');
    var _$saveSessionAs_FormCloseButton = $('#save_session_as_form_close_button');

    var _$sessionNameDisplay_span = $('#session_name_display');

    var _$createDokument_Link = $('#create_dokument_link');

    // ###################################
    // ##  Default Table HTML Elements  ##
    // ###################################

    var _$defaultTable_DropDown = $('#default_table_drop_down');
    var _$defaultTable_UploadLink = $('#default_table_upload_link');
    var _$defaultTable_UploadDialog = $('#default_table_upload_div');
    var _$defaultTable_UploadForm = $('#default_table_upload_form');
    var _$defaultTable_UploadFormButton = $('#default_table_upload_form_button');
    var _$defaultTable_UploadFormCloseButton = $('#default_table_upload_form_close_button');

    // ###################################
    // ##  Template File HTML Elements  ##
    // ###################################

    var _$templateFile_DropDown = $('#template_file_drop_down');
    var _$templateFile_UploadLink = $('#template_file_upload_link');
    var _$templateFile_UploadDialog = $('#template_file_upload_div');
    var _$templateFile_UploadForm = $('#template_file_upload_form');
    var _$templateFile_UploadFormButton = $('#template_file_upload_form_button');
    var _$templateFile_UploadFormCloseButton = $('#template_file_upload_form_close_button');

    // ###########################
    // ##  Tabs And Tab-Panels  ##
    // ###########################

    var _$tab_Figures = $('#tab_figures');
    var _$tab_Text = $('#tab_text');

    var _$tabPanel_Figures = $('#tab_panel_figures');
    var _$figures_DirDropDown = $('#figures_dir_drop_down');
    var _$figures_WildcardInput = $('#figures_wildcard_input');
    var _$figures_WildcardError = $('#wildcard_error');
    var _$figures_KeysDropDown = $('#figures_keys');
    var _$figure_Scaling = $('#figure_scaling');
    var _$figures_ThumbsDiv = $('#figures_thumbs_div');
    var _$figures_ThumbJQModel = $('#figures_thumb_jquery_model');
    var _$figure_PreviewDiv = $('#figure_preview_div');
    var _$figures_PreviewDiv = $('#figures_preview_div');
    var _$figures_CommentDiv = $('#figures_comment_div');
    var _$figures_Textarea = $('#figures_comment_t_area');
    _ui_common.disable_elem(_$figures_Textarea, true);

    var _$tabPanel_Text = $('#tab_panel_text');
    var _$text_KeyDropDown = $('#text_key_drop_down');
    var _$text_Textarea = $('#comment_text_area');
    _ui_common.disable_elem(_$text_Textarea, true);

    var tab_figures_clicked = function() {
        _$tabPanel_Figures.removeClass('hidden');
        _$tabPanel_Text.addClass('hidden');
        _$tab_Figures.addClass('selected');
        _$tab_Text.removeClass('selected');

        var selectedFigureKey = getSelectedFigureKey();
        var name_part = selectedFigureKey.substring(selectedFigureKey.indexOf('.'));
        var comment_key;
        if (_car_keys.getCommentKeyFor) {
            comment_key = _car_keys.getCommentKeyFor(name_part);
        } else {
            comment_key = '';
        }
        var isCommentKey = isString_and_NotEmpty(comment_key);
        _ui_common.disable_elem(_$figures_Textarea, !isCommentKey);
        _$figures_Textarea.val('');
        if (isCommentKey) {
            var text = _car_session.getProperty(comment_key);
            _$figures_Textarea.val(text);
        }
    };

    var tab_text_clicked = function() {
        _$tabPanel_Figures.addClass('hidden');
        _$tabPanel_Text.removeClass('hidden');
        _$tab_Figures.removeClass('selected');
        _$tab_Text.addClass('selected');

        var text_key = getSelectedTextKey();
        var isTextKey = isString_and_NotEmpty(text_key);
        _ui_common.disable_elem(_$text_Textarea, !isTextKey);
        _$text_Textarea.val('');
        if (isTextKey) {
            var text = _car_session.getProperty(text_key);
            _$text_Textarea.val(text);
        }
    };
    _$tab_Figures.click(tab_figures_clicked);
    _$tab_Text.click(tab_text_clicked);

    // ############################################################
    // #######         D O C U M E N T   K E Y S            #######
    // ############################################################

    _car_keys = {};

    function setKeys(car_keys) {
        _car_keys = car_keys;
        var $firstFigureOption = _$figures_KeysDropDown.find('option').first();
        _$figures_KeysDropDown.empty();
        _$figures_KeysDropDown.append($firstFigureOption);
        var figure_keys = _car_keys.get_figure_keys();
        for (var idx in figure_keys) {
            var key = figure_keys[idx];
            _$figures_KeysDropDown.append($('<option value="' + key + '">' + key + '</option>'));
        }

        var $firstTextOption = _$text_KeyDropDown.find('option').first();
        _$text_KeyDropDown.empty();
        _$text_KeyDropDown.append($firstTextOption);
        var text_keys = _car_keys.get_text_keys();
        for (var idx in text_keys) {
            var key = text_keys[idx];
            _$text_KeyDropDown.append($('<option value="' + key + '">' + key + '</option>'));
        }
    }

    // ############################################################
    // #######                S E S S I O N                 #######
    // ############################################################

    var _car_session = {};
    _car_session = new CAR_Session();

    function getSession() {
        return _car_session;
    }

    function setSession(car_session) {
        _car_session = car_session;
        _ui_common.removePreviewImage();
        update_ui();
        checkState();
        empty_and_disable_text_areas();
    }

    function set_values_from_default_table() {
        var figure_defaults = _car_keys.get_figure_defaults();
        for (var key in figure_defaults) {
            var fig_name = figure_defaults[key];
            _car_session.setProperty(key, fig_name);
            key = key.replace('.default', '');
            _car_session.setProperty(key, fig_name);
        }
        var scalings = _car_keys.get_figure_scalings();
        for (var key in scalings) {
            var val = scalings[key];
            _car_session.setProperty(key, val);
        }
        var text_defaults = _car_keys.get_text_defaults();
        for (var key in text_defaults) {
            var text = text_defaults[key];
            _car_session.setProperty(key, text);
            key = key.replace('.default', '');
            _car_session.setProperty(key, text);
        }
    }

    function resetSession() {
        var old = _car_session;
        _car_session = new CAR_Session();
        _car_session.setFiguresDirectory(old.getFiguresDirectory());
        _car_session.setDefaultTablePath(old.getDefaultTablePath());
        _car_session.setTemplateDocPath(old.getTemplateDocPath());
        _car_session.setFilename(old.getFilename());
        set_values_from_default_table()
    }

    function ajax_load_session(filename, ajax_callback) {
        var formData = new FormData(_$generalForm[0]);
        formData.append('session_name', filename);
        $.ajax({
            type:        'POST',
            url:         '/load_session',
            contentType: false,
            data:        formData,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                if (ajax_callback) {
                    ajax_callback();
                }
                var json_session = data.responseText;
                var session_properties = JSON.parse(json_session);
                setSession(new CAR_Session(session_properties));
            }
        });
    }

    function ajax_save_session(callback) {
        var formData = new FormData($('#general_form')[0]);
        formData.append('session', JSON.stringify(_car_session.getProperties()));
        $.ajax({
            type:        'POST',
            url:         '/save_session',
            contentType: false,
            data:        formData,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                var responseText = data.responseText;
                _ui_common.show_message(responseText, null, callback);
                _car_session.setSessionIsSaved();
                update_ui();
                checkState();
            }
        });
    }

    function ajax_save_session_as(filename, callback) {
        _car_session.setFilename(filename);
        ajax_save_session(callback);
    }

    function ajax_file_upload(upload_path, form, ajax_callback) {
        var form_data = new FormData(form[0]);
        $.ajax({
            type:        'POST',
            url:         '/upload/' + upload_path,
            data:        form_data,
            contentType: false,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                var msg = data.responseText;
                _ui_common.show_message(msg, null, ajax_callback);
            }
        });
    }

    function ajax_render_document() {
        function todo_after_dialog() {
            _ui_common.show_overlay();
            var formData = new FormData(_$generalForm[0]);
            formData.append('session', JSON.stringify(_car_session.getProperties()));
            $.ajax({
                type:        'POST',
                url:         '/render_document',
                data:        formData,
                contentType: false,
                cache:       false,
                processData: false,
                async:       true,
                complete:    function(data) {
                    var responseText = data.responseText;
                    responseText = responseText.trim();
                    if (responseText.indexOf('<a href') == 0) {
                        var msg = 'Document successfully rendered!\n';
                        _ui_common.show_message(msg, responseText);
                    } else {
                        _ui_common.show_message(responseText);
                    }
                }
            })
        }

        if (_car_session.isSessionChanged()) {
            var msg = 'The session was changed. Do you want to save the session?';
            _ui_common.show_yes_no_dialog(msg, {
                yesAction: function() {
                    ajax_save_session(todo_after_dialog);
                },
                noAction:  todo_after_dialog
            });
        } else {
            todo_after_dialog();
        }
    }

    function empty_and_disable_text_areas() {
        _$text_Textarea.val('');
        _ui_common.disable_elem(_$text_Textarea, true);
        _$figures_Textarea.val('');
        _ui_common.disable_elem(_$figures_Textarea, true);
    }

    function getSelectedFigureKey() {
        return _$figures_KeysDropDown.val();
    }

    function getSelectedTextKey() {
        return _$text_KeyDropDown.val();
    }

    function isSingleFigureKey(key) {
        return isString_and_NotEmpty(key) && key.indexOf('figure.') == 0;
    }

    function isMultiFigureKey(key) {
        return isString_and_NotEmpty(key) && key.indexOf('figures.') == 0;
    }

    // ############################################################
    // #######                 I M A G E S                  #######
    // ############################################################

    function getImages() {
        return _images;
    }

    function setImages(images) {
        _images = images;
        create_thumbnail_elements();
    }

    function selectCheckboxes(imageInfos) {
        for (var i in imageInfos) {
            var info = imageInfos[i];
            info.$thumb_div.find('input').prop('checked', true);
        }
    }

    function removeFromMultiFigure(key, imgName) {
        _car_session.removeFromMultiFigure(key, imgName);
        updateMultiFigureUi(key);
    }

    function updateMultiFigureUi(key) {
        _ui_common.uncheck_all_image_checkboxes();
        var names = _car_session.getMultiFigureNames(key);
        var imageInfos = getImageInfos(names);
        selectCheckboxes(imageInfos);
        _ui_common.set_multi_figure_view(imageInfos, removeFromMultiFigure);
    }

    function create_thumbnail_elements() {
        _$figures_ThumbsDiv.empty();
        for (var idx in _images) {
            //noinspection JSUnfilteredForInLoop
            var image_info = _images[idx];
            var thumb_url = image_info['thumb_url'];
            var url = image_info['url'];
            var name = image_info['name'];
            var width = image_info['width'];
            var height = image_info['height'];
            var $clone = _$figures_ThumbJQModel.clone();
            $clone.removeAttr('id');
            $clone.find('img').attr('src', thumb_url);
            $clone.find('a').attr('href', url);
            var $input = $clone.find('input');
            $input.val(url);
            $input.change(function() {
                var $checkbox = $(this);
                var text = $checkbox.parent().text().trim();
                var key = getSelectedFigureKey();
                if (isSingleFigureKey(key)) {
                    if ($checkbox.is(':checked')) {
                        _ui_common.set_preview_image_for_name(text);
                        if (_car_session.getProperty(key)) {
                            _ui_common.uncheck_all_image_checkboxes();
                            $checkbox.prop('checked', true);
                        }
                        _car_session.setProperty(key, text);
                    } else {
                        _ui_common.removePreviewImage();
                        _car_session.removeProperty(key, text);
                    }
                } else {
                    if ($checkbox.is(':checked')) {
                        _car_session.addToMultiFigure(key, text);
                    } else {
                        _car_session.removeFromMultiFigure(key, text);
                    }
                    var imageNames = _car_session.getMultiFigureNames(key);
                    var imageInfos = getImageInfos(imageNames);
                    _ui_common.set_multi_figure_view(imageInfos, removeFromMultiFigure);
                }
            });
            $clone.find('label').append(name);
            $clone.appendTo(_$figures_ThumbsDiv);
            $clone.append('#' + idx + ' &nbsp; width: ' + width + ' &nbsp; height: ' + height);
            image_info.$thumb_div = $clone;
        }
        checkState();
    }

    function getImageInfos(names) {
        var array = [];
        for (var i in names) {
            var name = names[i];
            for (var j in _images) {
                var info = _images[j];
                if(name == info['name']) {
                    array.push(info)
                }
            }
        }
        return array;
    }

    function ajax_get_document_keys(default_table_path, call_back) {
        var formData = new FormData(_$generalForm[0]);
        formData.append('default_table_path', default_table_path);
        $.ajax({
            type:        'POST',
            url:         '/get_document_keys',
            contentType: false,
            data:        formData,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                var json_keys = data.responseText;
                json_keys = json_keys.trim();
                if (json_keys.indexOf('{') == 0) {
                    var keys = JSON.parse(json_keys);
                    call_back(keys);
                } else {
                    _ui_common.show_message("Error while requesting key's.");
                }
            }
        });
    }

    function checkState() {
        var defaultTableSelected = _$defaultTable_DropDown.prop('selectedIndex') > 0;
        _ui_common.disable_elem(_$saveSession_Button, !defaultTableSelected || !_car_session.hasFileName());
        _ui_common.disable_elem(_$saveSessionAs_Button, !defaultTableSelected);

        var figure_key = _$figures_KeysDropDown.val();
        var figureKeyIsSelected = isString_and_NotEmpty(figure_key);
        _ui_common.disable_elem(_$figures_ThumbsDiv.find('input'), !figureKeyIsSelected);
    }

    function remove_image_dir_dependent_keys_from_session() {
        // @todo
        // NOT REMOVE key defaults!
        // remove_keys_from_session(get_figure_keys());
    }

    //@todo needed? should it be moved to session?
    function remove_keys_from_session(key_array) {
        var i;
        for (i in key_array) {
            var key = key_array[i];
            delete _session[key];
        }
    }

    function figures_dir_change(dir, callback) {
        _ui_common.removePreviewImage();
        remove_image_dir_dependent_keys_from_session();
        _car_session.setFiguresDirectory(dir);
        ajax_get_images(dir, callback);
    }

    // ############################################################
    // ############################################################
    // ############################################################

    function ajax_get_images(dir, callback) {
        var formData = new FormData(_$generalForm[0]);
        formData.append('images_dir', dir);
        $.ajax({
            type:        'POST',
            url:         '/get_images',
            contentType: false,
            data:        formData,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                var json_images = data.responseText;
                var images = JSON.parse(json_images);
                setImages(images);
                if (callback) {
                    callback();
                }
            }
        });
    }

    function ajax_update_component_options($element, ajax_callback) {
        var component_id = $element[0].id;
        var formData = new FormData(_$generalForm[0]);
        formData.append('component_id', component_id);
        $.ajax({
            type:        'POST',
            url:         '/update_component_options',
            contentType: false,
            data:        formData,
            cache:       false,
            processData: false,
            async:       true,
            complete:    function(data) {
                var responseText = data.responseText;
                var $new_options = $(responseText.trim());
                var $firstOption = $element.find('option').first();
                $element.empty();
                $element.append($firstOption);
                $new_options.appendTo($element);
                if (ajax_callback) {
                    ajax_callback();
                }
            }
        });
    }

    function compute_height_of_different_ui_elements() {
        var figures_selected = _$tab_Figures.hasClass('selected');

        if (!figures_selected) {
            tab_figures_clicked();
        }

        var images_top = _$figures_ThumbsDiv.position().top;
        var footer_height = _$footer_div.outerHeight(true);
        var height = $(window).height() - images_top - footer_height - 22;
        _$figures_ThumbsDiv.css('height', height + 'px');
        var commentHeight = _$figures_CommentDiv.outerHeight(true);
        var previewHeight = height - commentHeight;
        _$figures_PreviewDiv.css('height', previewHeight + 'px');
        _$figure_PreviewDiv.css('height', previewHeight + 'px');

        var tab_height = _$tabPanel_Figures.outerHeight();
        _$tabPanel_Text.css('height', tab_height + 'px');

        tab_text_clicked();

        var $text_area_label = $('label[for="comment_text_area"]');
        var text_label_top = $text_area_label.position().top;
        var text_label_height = $text_area_label.outerHeight();
        var text_tab_top = _$tabPanel_Text.position().top;
        var text_tab_inner_height = _$tabPanel_Text.innerHeight();

        var inner_offset = text_label_top - text_tab_top + text_label_height;
        _$text_Textarea.css('height', text_tab_inner_height - inner_offset - 12);

        if (figures_selected) {
            tab_figures_clicked();
        }
    }

    // ############################################################
    // ############################################################
    // ############################################################

    function setSelectedFiguresDirectoryToDropDown(dir) {
        if (!isString_and_NotEmpty(dir)) {
            return;
        }
        if (dir == _$figures_DirDropDown.val()) {
            return;
        }
        _$figures_DirDropDown.val(dir);
        ajax_get_images(dir);
    }

    function isString_and_NotEmpty(value) {
        return value != null && (typeof value == 'string') && value.trim().length > 0;
    }

    function isNull_or_NotStr_or_Empty(value) {
        return !isString_and_NotEmpty(value);
    }

    function updateKeys(currentDefaultTablePath, ajax_callback) {
        if (currentDefaultTablePath.trim().length > 0) {
            ajax_get_document_keys(currentDefaultTablePath, function(keys) {
                setKeys(new CAR_Keys(keys));
                if (ajax_callback) {
                    ajax_callback();
                }
            });
        } else {
            setKeys(new CAR_Keys());
        }
    }

    function update_ui() {
        var sessionName = _car_session.getFilename();
        var currentTemplateDocPath = _car_session.getTemplateDocPath();
        var currentDefaultTablePath = _car_session.getDefaultTablePath();
        var currentFiguresDirectory = _car_session.getFiguresDirectory();

        if (isString_and_NotEmpty(sessionName)) {
            _$sessionNameDisplay_span.text(sessionName);
        }

        _$templateFile_DropDown.val(currentTemplateDocPath);

        var old_default_table_path = _$defaultTable_DropDown.val();
        if (currentDefaultTablePath != old_default_table_path) {
            _$defaultTable_DropDown.val(currentDefaultTablePath);
            updateKeys(currentDefaultTablePath);
        }

        setSelectedFiguresDirectoryToDropDown(currentFiguresDirectory);
    }

    function initUI() {
        ajax_update_component_options(_$templateFile_DropDown);
        ajax_update_component_options(_$defaultTable_DropDown);
        ajax_update_component_options(_$figures_DirDropDown);
        compute_height_of_different_ui_elements();
        $(window).resize(compute_height_of_different_ui_elements);
    }

    var _sessionEvents = {
        bind: function() {

            var showLoadSessionDialog = function() {
                _ui_common.show_elem_placed(
                        _$loadSession_Button,
                        _$loadSession_Dialog
                );
            };

            _$loadSession_Button.click(function() {
                _ui_common.show_overlay();
                ajax_update_component_options(_$loadSession_FormDropDown, showLoadSessionDialog);
            });

            _$loadSession_FormCloseButton.click(function() {
                _ui_common.close_dialog(_$loadSession_Dialog);
            });

            _$loadSession_FormButton.click(function() {
                _ui_common.hide_elem(_$loadSession_Dialog);
                var session_file = _$loadSession_FormDropDown.val();
                ajax_load_session(session_file, _ui_common.hide_overlay);
            });

            _$saveSession_Button.click(function() {
                ajax_save_session();
            });

            _$saveSessionAs_Button.click(function() {
                _ui_common.show_overlay();
                _ui_common.show_elem_placed(
                        _$saveSessionAs_Button,
                        _$saveSessionAs_Dialog
                );
            });

            var closeSaveSessionAsDialog = function() {
                _ui_common.close_dialog(_$saveSessionAs_Dialog);
            };

            _$saveSessionAs_FormButton.click(function() {
                var filename = _$saveSessionAs_FormNameInput.val();
                ajax_save_session_as(filename, closeSaveSessionAsDialog);
            });

            _$saveSessionAs_FormCloseButton.click(function() {
                closeSaveSessionAsDialog();
            });
        }
    };

    var _defaultTableEvents = {
        bind: function() {

            _$defaultTable_DropDown.change(function() {
                var val = _$defaultTable_DropDown.val();
                _car_session.setDefaultTablePath(val);
                function afterKeysAreUpdated() {
                    resetSession();
                    _ui_common.uncheck_all_image_checkboxes();
                    update_ui();
                    checkState();
                }

                updateKeys(val, afterKeysAreUpdated);
            });

            _$defaultTable_UploadLink.click(function() {
                _ui_common.show_overlay();
                _ui_common.show_elem_placed(
                        _$defaultTable_DropDown,
                        _$defaultTable_UploadDialog
                );
            });

            _$defaultTable_UploadFormButton.click(function() {
                var todo_after_upload = function() {
                    _ui_common.close_dialog(_$defaultTable_UploadDialog);
                    ajax_update_component_options(_$defaultTable_DropDown);
                };
                ajax_file_upload(
                        'default_table',
                        _$defaultTable_UploadForm,
                        todo_after_upload
                );
            });

            _$defaultTable_UploadFormCloseButton.click(function() {
                _ui_common.close_dialog(_$defaultTable_UploadDialog);
            });
        }
    };

    var _templateFileEvents = {
        bind: function() {

            _$templateFile_DropDown.change(function() {
                var path = _$templateFile_DropDown.val();
                _car_session.setTemplateDocPath(path);
                checkState();
            });

            _$templateFile_UploadLink.click(function() {
                _ui_common.show_overlay();
                _ui_common.show_elem_placed(
                        _$templateFile_DropDown,
                        _$templateFile_UploadDialog
                );
            });

            _$templateFile_UploadFormButton.click(function() {
                var todo_after_upload = function() {
                    _ui_common.close_dialog(_$templateFile_UploadDialog);
                    ajax_update_component_options(_$templateFile_DropDown);
                };
                ajax_file_upload(
                        'template',
                        _$templateFile_UploadForm,
                        todo_after_upload
                );
            });

            _$templateFile_UploadFormCloseButton.click(function() {
                _ui_common.close_dialog(_$templateFile_UploadDialog);
            });
        }
    };

    var _document_render_events = {
        bind: function() {
            _$createDokument_Link.click(function() {
                ajax_render_document();
            });
        }
    };

    function show_all_thumbnails() {
        for (var i in _images) {
            var image_info = _images[i];
            var $div = image_info['$thumb_div'];
            $div.removeClass(_class_hidden);
        }
    }

    function check_wildcard() {
        var i;
        var $div;
        var image_info;
        _$figures_WildcardError.addClass(_class_hidden);
        var val = _$figures_WildcardInput.val();
        if (isString_and_NotEmpty(val)) {
            try {
                var regExp = new RegExp(val, 'i');
                for (i in _images) {
                    image_info = _images[i];
                    var image_name = image_info['name'];
                    $div = image_info['$thumb_div'];
                    if (regExp.test(image_name)) {
                        $div.removeClass(_class_hidden);
                    } else {
                        $div.addClass(_class_hidden);
                    }
                }
            }
            catch (err) {
                _$figures_WildcardError.removeClass(_class_hidden);
                show_all_thumbnails();
            }
        } else {
            show_all_thumbnails();
        }
    }

    var _figure_tab_events = {
        bind: function() {
            _$figures_DirDropDown.change(function() {
                var dir = _$figures_DirDropDown.val();
                figures_dir_change(dir, check_wildcard);
            });

            _$figures_Textarea.blur(function() {
                var key = getSelectedFigureKey();
                var name_part = key.substring(key.indexOf('.'));
                var comment_key = _car_keys.getCommentKeyFor(name_part);
                var isCommentKey = isString_and_NotEmpty(comment_key);
                if (isCommentKey) {
                    var value = _$figures_Textarea.val();
                    _car_session.setProperty(comment_key, value);
                }
            });

            _$figures_KeysDropDown.change(function() {
                checkState();
                var key = getSelectedFigureKey();
                var name_part = key.substring(key.indexOf('.'));

                var comment_key = _car_keys.getCommentKeyFor(name_part);
                var isCommentKey = isString_and_NotEmpty(comment_key);
                _ui_common.disable_elem(_$figures_Textarea, !isCommentKey);
                _$figures_Textarea.val('');
                if (isCommentKey) {
                    var text = _car_session.getProperty(comment_key);
                    _$figures_Textarea.val(text);
                }

                if (isMultiFigureKey(key)) {
                    updateMultiFigureUi(key);
                } else {
                    _ui_common.uncheck_all_image_checkboxes();
                    var value = _car_session.getProperty(key);
                    _ui_common.select_figure_checkbox_with_name(value);
                    _ui_common.set_preview_image_for_name(value);
                }
                var scale = _car_session.getScale(key);
                _$figure_Scaling.val(scale);
            });

            _$figure_Scaling.blur(function() {
                var key = getSelectedFigureKey();
                if (isNull_or_NotStr_or_Empty(key)) {
                    return;
                }
                var str = _$figure_Scaling.val().trim();
                str = str.replace(',', '.');
                var val = parseFloat(str);
                if (str != '' + val) {
                    _ui_common.show_message('Input corrected to "' + val + '"');
                }
                _$figure_Scaling.val(val);
                str = _$figure_Scaling.val().trim();
                _car_session.setScale(key, str);
            });

            _$figures_WildcardInput.keyup(check_wildcard);
        }
    };

    var _text_tab_events = {
        bind: function() {

            _$text_Textarea.blur(function() {
                var text_key = getSelectedTextKey();
                var isTextKey = isString_and_NotEmpty(text_key);
                if (isTextKey) {
                    var value = _$text_Textarea.val();
                    _car_session.setProperty(text_key, value);
                }
            });

            _$text_KeyDropDown.change(function() {
                checkState();
                var text_key = getSelectedTextKey();
                var isTextKey = isString_and_NotEmpty(text_key);
                _ui_common.disable_elem(_$text_Textarea, !isTextKey);
                _$text_Textarea.val('');
                if (isTextKey) {
                    var text = _car_session.getProperty(text_key);
                    _$text_Textarea.val(text);
                }
            });
        }
    };

    function bindEvents() {
        _sessionEvents.bind();
        _defaultTableEvents.bind();
        _templateFileEvents.bind();
        _document_render_events.bind();
        _figure_tab_events.bind();
        _text_tab_events.bind();
    }

    initUI();
    checkState();
    bindEvents();
};

var $C;

$(document).ready(function() {
    $C = new CAR_Tool();
});


