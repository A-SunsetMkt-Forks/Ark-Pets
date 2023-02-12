/** Copyright (c) 2022-2023, Harry Huang
 * At GPL-3.0 License
 */
package com.isharryh.arkpets.controllers;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.isharryh.arkpets.ArkConfig;
import com.isharryh.arkpets.ArkHomeFX;
import com.isharryh.arkpets.utils.AssetCtrl;
import com.isharryh.arkpets.utils.IOUtils.*;
import com.isharryh.arkpets.utils.PopupUtils.*;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.ZipException;


public class Homepage {
    private final String urlStyleSheet = Objects.requireNonNull(getClass().getResource("/UI/Main.css")).toExternalForm();
    private int bufferSize = 8 * 1024;
    private int httpTimeout = 30 * 1000;
    private boolean httpsTrustAll = false;
    private final String urlApi = "https://arkpets.tfev.top/p/arkpets/client/api.php";
    private final String urlOfficial = "https://arkpets.tfev.top/p/arkpets/?from=client";
    private final String urlModelsZip = "https://github.com/isHarryh/Ark-Models/archive/refs/heads/main.zip";
    private final String urlModelsData = "https://raw.githubusercontent.com/isHarryh/Ark-Models/main/models_data.json";
    private final String[] urlSourcePrefix = {
            "https://ghproxy.com/?q=",
            ""
    };
    private final String tempDirPath = "temp/";
    private final String tempModelsUnzipDirPath = tempDirPath + "models_unzipped/";
    private final String tempModelsZipCachePath = tempDirPath + "ArkModels.zip";
    private final String fileModelsDataPath = "models_data.json";
    private final String tempQueryVersionCachePath = tempDirPath + "ApiQueryVersionCache";

    @FXML
    private Pane Sidebar;
    @FXML
    private JFXButton menuBtn1;
    @FXML
    private JFXButton menuBtn2;
    @FXML
    private JFXButton menuBtn3;
    @FXML
    private Pane wrapper1;
    @FXML
    private Pane wrapper2;
    @FXML
    private Pane wrapper3;
    @FXML
    private JFXButton startBtn;

    @FXML
    private Pane loadFailureTip;
    @FXML
    private JFXButton searchModelConfirm;
    @FXML
    private JFXButton searchModelReset;
    @FXML
    private JFXButton searchModelRandom;
    @FXML
    private JFXButton searchModelReload;
    @FXML
    private JFXTextField searchModelInput;
    @FXML
    private VBox searchModelList;
    @FXML
    private Label selectedModelName;
    @FXML
    private Label selectedModelAppellation;
    @FXML
    private Label selectedModelSkinGroupName;
    @FXML
    private Label selectedModelType;
    @FXML
    private ImageView selectedModelView;

    @FXML
    private JFXCheckBox configBehaviorAllowWalk;
    @FXML
    private JFXCheckBox configBehaviorAllowSit;
    @FXML
    private JFXSlider configBehaviorAiActivation;
    @FXML
    private Label configBehaviorAiActivationValue;
    @FXML
    private JFXCheckBox configBehaviorAllowInteract;
    @FXML
    private JFXCheckBox configBehaviorDoPeerRepulsion;

    @FXML
    private ChoiceBox<Float> configDisplayScale;
    @FXML
    private ChoiceBox<Integer> configDisplayFps;
    @FXML
    private JFXSlider configDisplayMarginBottom;
    @FXML
    private Label configDisplayMarginBottomValue;
    @FXML
    private JFXButton manageModelCheck;
    @FXML
    private JFXButton manageModelFetch;
    @FXML
    private JFXButton manageModelVerify;
    @FXML
    private Label aboutQueryUpdate;
    @FXML
    private Label aboutVisitWebsite;

    private AssetCtrl selectedModelAsset;
    private Pane selectedModelItem;
    private AssetCtrl[] foundModelAssets = {};
    private Pane[] foundModelItems = {};

    public ArkConfig config;
    public JSONObject modelsDatasetFull;

    public Homepage() {
    }

    public void initialize() {
        System.out.println("[AH] Initializing (JavaFX" + System.getProperty("javafx.version") + " Java" + System.getProperty("java.version") + ")");
        config = ArkConfig.getConfig();
        initMenuBtn(menuBtn1, 1);
        initMenuBtn(menuBtn2, 2);
        initMenuBtn(menuBtn3, 3);
        initWrapper(1);
        initModelSearch();
        initModelManage();
        initAbout();
        menuBtn1.getStyleClass().add("menu-btn-active");
        Platform.runLater(() -> {
            initModelAssets(false);
            loadFailureTip.setVisible(foundModelItems.length == 0);
            startBtn.setDisable(foundModelItems.length == 0);
            dealModelSearch("");
        });
        System.out.println("[AH] Initialized");
    }

    private void initMenuBtn(Button $menuBtn, int $boundIdx) {
        $menuBtn.getStyleClass().setAll("menu-btn");
        $menuBtn.setOnAction(e -> {
            initWrapper($boundIdx);
            menuBtn1.getStyleClass().setAll("menu-btn");
            menuBtn2.getStyleClass().setAll("menu-btn");
            menuBtn3.getStyleClass().setAll("menu-btn");
            $menuBtn.getStyleClass().add("menu-btn-active");
        });
    }

    private void initWrapper(int $activeIdx) {
        wrapper1.setVisible(wrapper1.getId().contains(String.valueOf($activeIdx)));
        wrapper2.setVisible(wrapper2.getId().contains(String.valueOf($activeIdx)));
        wrapper3.setVisible(wrapper3.getId().contains(String.valueOf($activeIdx)));
        initConfigBehavior();
        initConfigDisplay();
    }

    private void initModelSearch() {
        searchModelInput.setPromptText("输入关键字");
        searchModelInput.setOnKeyPressed(e -> {
            if (e.getCode().getName().equals(KeyCode.ENTER.getName()))
                dealModelSearch(searchModelInput.getText());
        });
        searchModelConfirm.setOnAction(e -> dealModelSearch(searchModelInput.getText()));
        searchModelReset.setOnAction(e -> dealModelSearch(""));
        searchModelRandom.setOnAction(e -> dealModelRandom());
        searchModelReload.setOnAction(e -> dealModelReload());
    }

    private boolean initModelDataset(boolean $doPopNotice) {
        try {
            try {
                modelsDatasetFull = Objects.requireNonNull(JSONObject.parseObject(FileUtil.readString(new File(fileModelsDataPath), "UTF-8")));
                if (!modelsDatasetFull.containsKey("data"))
                    throw new JSONException("The key 'data' may not in the dataset.");
                if (!modelsDatasetFull.containsKey("storageDirectory"))
                    throw new JSONException("The key 'storageDirectory' may not in the dataset.");
                // TODO check dataset capability
                return true;
            } catch (Exception e) {
                modelsDatasetFull = null;
                throw e;
            }
        } catch (FileNotFoundException e) {
            if ($doPopNotice)
                popNotice(IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING), "模型载入失败", "模型未成功载入：未找到模型数据集。",
                        "模型数据集文件 " + fileModelsDataPath + " 可能不在工作目录下。\n请先前往[选项]进行模型下载。").show();
        } catch (IOException e) {
            e.printStackTrace();
            if ($doPopNotice)
                popNotice(IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING), "模型载入失败", "模型未成功载入：未知原因。",
                        "失败原因概要：" + e.getLocalizedMessage()).show();
        }
        return false;
    }

    private boolean initModelAssets(boolean $doPopNotice) {
        try {
            try {
                if (!initModelDataset($doPopNotice))
                    return false;
                // Find every model.
                ArrayList<AssetCtrl> foundModelAssetsL = new ArrayList<>();
                for (String key : modelsDatasetFull.getJSONObject("storageDirectory").keySet())
                    foundModelAssetsL.addAll(Arrays.asList(AssetCtrl.getAssetList(new File(modelsDatasetFull.getJSONObject("storageDirectory").getString(key)), modelsDatasetFull.getJSONObject("data"))));
                foundModelAssets = foundModelAssetsL.toArray(new AssetCtrl[0]);
                if (foundModelAssets.length == 0)
                    throw new RuntimeException("Found no assets in the target directories.");
                // Models to menu items.
                ArrayList<Pane> foundModelItemsL = new ArrayList<>();
                for (AssetCtrl asset : foundModelAssets)
                    foundModelItemsL.add(getMenuItem(asset, searchModelList));
                foundModelItems = foundModelItemsL.toArray(new Pane[0]);
                return true;
            } catch (Exception e) {
                foundModelAssets = new AssetCtrl[0];
                foundModelItems = new Pane[0];
                throw e;
            }
        } catch (RuntimeException e) {
            if ($doPopNotice)
                popNotice(IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING), "模型载入失败", "模型未成功载入：未能成功解析模型数据集。",
                        "可能是数据集损坏、版本不兼容或模型存放位置错误。\n失败原因概要：" + e.getLocalizedMessage()).show();
        }
        return false;
    }

    private void initConfigBehavior() {
        configBehaviorAllowWalk.setSelected(config.behavior_allow_walk);
        configBehaviorAllowWalk.setOnAction(e -> {
            config.behavior_allow_walk = configBehaviorAllowWalk.isSelected();
            config.saveConfig();
        });
        configBehaviorAllowSit.setSelected(config.behavior_allow_sit);
        configBehaviorAllowSit.setOnAction(e -> {
            config.behavior_allow_sit = configBehaviorAllowSit.isSelected();
            config.saveConfig();
        });
        configBehaviorAiActivation.setMax(8);
        configBehaviorAiActivation.setMin(0);
        configBehaviorAiActivation.setMajorTickUnit(1);
        configBehaviorAiActivation.setMinorTickCount(0);
        configBehaviorAiActivation.setShowTickLabels(false);
        configBehaviorAiActivation.setValue(config.behavior_ai_activation);
        configBehaviorAiActivation.valueProperty().addListener(((observable, oldValue, newValue) -> {
            long value = Math.round((double)newValue);
            configBehaviorAiActivation.setValue(value);
            configBehaviorAiActivationValue.setText(String.valueOf(value));
            config.behavior_ai_activation = (int)value;
            config.saveConfig();
        }));
        configBehaviorAiActivationValue.setText(String.valueOf(config.behavior_ai_activation));
        configBehaviorAllowInteract.setSelected(config.behavior_allow_interact);
        configBehaviorAllowInteract.setOnAction(e -> {
            config.behavior_allow_interact = configBehaviorAllowInteract.isSelected();
            config.saveConfig();
        });
        configBehaviorDoPeerRepulsion.setSelected(config.behavior_do_peer_repulsion);
        configBehaviorDoPeerRepulsion.setOnAction(e -> {
            config.behavior_do_peer_repulsion = configBehaviorDoPeerRepulsion.isSelected();
            config.saveConfig();
        });
    }

    private void initConfigDisplay() {
        configDisplayScale.getItems().setAll(0.5f, 0.75f, 1f, 1.25f, 1.5f, 2f);
        configDisplayScale.getSelectionModel().select(config.display_scale);
        configDisplayScale.valueProperty().addListener(observable -> {
            if (configDisplayScale.getValue() != null) {
                config.display_scale = configDisplayScale.getValue();
                config.saveConfig();
            }
        });
        configDisplayFps.getItems().setAll(25, 30, 45, 60);
        configDisplayFps.getSelectionModel().select(Integer.valueOf(config.display_fps));
        configDisplayFps.valueProperty().addListener(observable -> {
            if (configDisplayFps.getValue() != null) {
                config.display_fps = configDisplayFps.getValue();
                config.saveConfig();
            }
        });
        configDisplayMarginBottom.setMax(120);
        configDisplayMarginBottom.setMin(0);
        configDisplayMarginBottom.setMajorTickUnit(10);
        configDisplayMarginBottom.setMinorTickCount(5);
        configDisplayMarginBottom.setValue(config.display_margin_bottom);
        configDisplayMarginBottom.valueProperty().addListener(((observable, oldValue, newValue) -> {
            int value = (int)Math.round((double)newValue);
            //configDisplayMarginBottom.setValue(value);
            configDisplayMarginBottomValue.setText(String.valueOf(value));
            config.display_margin_bottom = value;
            config.saveConfig();
        }));
        configDisplayMarginBottomValue.setText(String.valueOf(config.display_margin_bottom));
    }

    private void initModelManage() {
        manageModelCheck.setOnAction(e -> foregroundCheckModels());
        manageModelFetch.setOnAction(e -> foregroundFetchModels());
        manageModelVerify.setOnAction(e -> foregroundVerifyModels());
    }

    private void initAbout() {
        aboutQueryUpdate.setOnMouseClicked(e -> foregroundCheckUpdate(true, "manual"));
        aboutVisitWebsite.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(urlOfficial));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public Dialog<ButtonType> foregroundTask(Task $task, String $title, String $header, String $defaultContent, Boolean $cancelable) {
        Dialog<ButtonType> dialog = new Dialog<>();
        ProgressBar bar = new ProgressBar(-1);
        DialogPane dialogPane = dialog.getDialogPane();
        if ($cancelable) {
            dialogPane.getButtonTypes().add(ButtonType.CANCEL);
            Button cancelBtn = (Button)(dialogPane.lookupButton(ButtonType.CANCEL));
            cancelBtn.setOnAction(e -> $task.cancel());
            dialog.setOnCloseRequest(e -> $task.cancel());
        }
        dialog.setTitle($title);
        dialog.setHeaderText($header);
        dialog.setContentText($defaultContent);
        dialog.setResizable(false);
        dialog.setGraphic(bar);
        dialog.show();
        final double[] cachedProgress = {-1};
        $task.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (Math.abs((double)newValue - cachedProgress[0]) >= 0.005) {
                cachedProgress[0] = (double)newValue;
                bar.setProgress((double)newValue);
            }
        });
        $task.messageProperty().addListener(((observable, oldValue, newValue) -> {
            dialog.setContentText(newValue);
        }));
        $task.setOnCancelled(e -> {
            System.out.println("[AH]A foreground task was cancelled.");
            dialog.close();
        });
        $task.setOnFailed(e -> {
            System.err.println("[AH]A foreground task failed, cause:");
            $task.getException().printStackTrace();
            popError($task.getException()).show();
            if (!$cancelable) {
                dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            }
            dialog.close();
        });
        $task.setOnSucceeded(e -> {
            System.out.println("[AH]A foreground task done.");
            if (!$cancelable) {
                dialogPane.getButtonTypes().add(ButtonType.CLOSE);
            }
            dialog.close();
        });
        $task.stateProperty().addListener(((observable, oldValue, newValue) -> {
            //System.out.println(Thread.currentThread().getName() + ": " + newValue.toString());
        }));
        Thread thread = new Thread($task);
        thread.start();
        return dialog;
    }

    public Dialog<ButtonType> popError(Throwable $e) {
        Dialog<ButtonType> dialog = new Dialog<>();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setTitle("发生异常");
        dialog.setHeaderText("啊哦~ ArkPets启动器抛出了一个异常。");
        dialog.setContentText("请重试操作，或查看帮助文档。如需联系开发者，请提供下述信息：");
        dialog.setGraphic(IconUtil.getIcon(IconUtil.ICON_DANGER_ALT, IconUtil.COLOR_DANGER));
        dialog.setOnCloseRequest(e -> dialog.close());
        TextArea expandable = new TextArea();
        expandable.appendText("[Exception] " + $e.getClass().getSimpleName() + "\n");
        expandable.appendText("[Message] " + ($e.getLocalizedMessage() != null ? $e.getLocalizedMessage() : "") + "\n");
        expandable.appendText("\n[StackTrace]\nCaused by " + $e.getClass().getCanonicalName() + ": " + $e.getMessage() + "\n");
        for (StackTraceElement ste : $e.getStackTrace())
            expandable.appendText("  at " + ste + "\n");
        expandable.setEditable(false);
        expandable.setWrapText(true);
        expandable.setFocusTraversable(true);
        expandable.getStylesheets().add(urlStyleSheet);
        expandable.getStyleClass().add("expandable-field");
        AnchorPane expandableContainer = new AnchorPane(expandable);
        Insets expandablePadding = new Insets(8);
        dialogPane.setExpanded(false);
        dialogPane.setExpandableContent(expandableContainer);
        dialog.widthProperty().addListener(((observable, oldValue, newValue) -> {
            expandable.setLayoutX(expandablePadding.getTop());
            expandable.setLayoutY(expandablePadding.getLeft());
            expandable.setPrefWidth(dialogPane.getWidth() - expandablePadding.getLeft() - expandablePadding.getRight());
            dialog.setResizable(false);
        }));
        dialogPane.getButtonTypes().add(ButtonType.OK);

        if ($e instanceof FileNotFoundException) {
            dialog.setContentText("原因是未找到某个文件或目录，请重试操作。详细信息：");
        }
        if ($e instanceof NetUtil.HttpResponseCodeException) {
            //TODO 准确获知错误码
            dialog.setHeaderText("啊哦~ 请求的服务器返回了一个HTTP错误码。");
            if ($e instanceof NetUtil.HttpRedirected) {
                dialog.setContentText("错误码[3XX]，可能是该地址被重定向。详细信息：");
            }
            if ($e instanceof NetUtil.HttpClientError) {
                dialog.setContentText("错误码[4XX]，详细信息：");
            }
            if ($e instanceof NetUtil.HttpServerError) {
                dialog.setContentText("错误码[5XX]，可能是服务器故障。详细信息：");
            }
        }
        if ($e instanceof UnknownHostException) {
            dialog.setHeaderText("啊哦~ 发生了网络问题。");
            dialog.setContentText("原因是找不到服务器地址，可能是因为网络未连接或DNS解析失败，请尝试更换网络环境、检查防火墙和代理设置。");
        }
        if ($e instanceof ConnectException) {
            dialog.setHeaderText("啊哦~ 发生了网络问题。");
            dialog.setContentText("原因是无法建立网络连接，请尝试更换网络环境、检查防火墙和代理设置。");
        }
        if ($e instanceof SocketTimeoutException) {
            dialog.setHeaderText("啊哦~ 发生了网络问题。");
            dialog.setContentText("原因是接收超时，请尝试更换网络环境、检查防火墙和代理设置。");
        }
        if ($e instanceof SSLException) {
            dialog.setHeaderText("啊哦~ 发生了网络问题。");
            dialog.setContentText("原因是SSL相关的证书错误，请检查代理设置。您也可以尝试[信任所有证书]后重试刚才的操作。");
            dialogPane.getButtonTypes().add(ButtonType.APPLY);
            Button apply = (Button)dialogPane.lookupButton(ButtonType.APPLY);
            apply.setText("信任所有证书");
            apply.setOnAction(e -> {
                httpsTrustAll = true;
            });
        }
        if ($e instanceof ZipException) {
            dialog.setContentText("原因是压缩文件相关错误。推测可能是下载源问题，请尝试更换下载源。");
        }
        //dialog.show();
        return dialog;
    }

    public Dialog<ButtonType> popNotice(Node $graphic, String $title, String $header, String $content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.setTitle($title);
        dialog.setHeaderText($header);
        dialog.setContentText($content);
        dialog.setGraphic($graphic);
        dialogPane.getButtonTypes().add(ButtonType.OK);
        //dialog.show();
        return dialog;
    }

    private void foregroundCheckModels() {
        try {
            Files.createDirectories(new File(tempDirPath).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!initModelDataset(true))
            return;
        Task<Boolean> task = createDownloadTask(urlModelsData, tempDirPath + fileModelsDataPath);
        Dialog<ButtonType> dialog = foregroundTask(task, "检查模型更新", "正在下载模型版本信息...", "正在尝试建立连接", true);
        task.setOnSucceeded(e -> {
            dialog.close();
            try {
                // TODO do judgment more precisely
                if (FileUtil.getMD5(new File(fileModelsDataPath)).equals(FileUtil.getMD5(new File(tempDirPath + fileModelsDataPath)))) {
                    popNotice(IconUtil.getIcon(IconUtil.ICON_SUCCESS_ALT, IconUtil.COLOR_SUCCESS), "检查模型更新", "当前模型版本与远程仓库一致。",
                            "提示：远程仓库的版本不一定和游戏同步更新。").show();
                } else {
                    popNotice(IconUtil.getIcon(IconUtil.ICON_INFO_ALT, IconUtil.COLOR_INFO), "检查模型更新", "当前模型版本与远程仓库有差异。",
                            "可以重新下载模型，以进行更新模型版本。").show();
                }
            } catch (IOException ex) {
                popError(ex).show();
            }
        });
    }

    private void foregroundFetchModels() {
        try {
            Files.createDirectories(new File(tempDirPath).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //1
        // TODO change download source
        Task<Boolean> task1 = createDownloadTask(urlModelsZip, tempModelsZipCachePath);
        Dialog<ButtonType> task1dialog = foregroundTask(task1, "正在更新模型", "正在下载模型资源文件...", "正在尝试建立连接", true);
        task1.setOnSucceeded(e1 -> {
            task1dialog.close();
            //2
            Task<Boolean> task2 = createUnzipTask(tempModelsZipCachePath, tempModelsUnzipDirPath);
            Dialog<ButtonType> task2dialog = foregroundTask(task2, "正在更新模型", "正在解压模型资源文件...", "这可能需要十几秒", false);
            task2.setOnSucceeded(e2 -> {
                task2dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                task2dialog.close();
                //3
                Task<Boolean> task3 = createModelsMovingTask(tempModelsUnzipDirPath, fileModelsDataPath);
                Dialog<ButtonType> task3dialog = foregroundTask(task3, "正在更新模型", "正在应用模型更新...", "即将完成", false);
                task3.setOnSucceeded(e3 -> {
                    task3dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                    task3dialog.close();
                    dealModelReload();
                });
            });
        });
    }

    private void foregroundVerifyModels() {
        if (!initModelDataset(true))
            return;
        final Node[] dialogGraphic = new Node[1];
        final String[] dialogHeader = new String[1];
        final String[] dialogContent = new String[1];
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                this.updateProgress(0.1, 1);
                Path rootPath = new File("").toPath();
                int rootPathCount = rootPath.getNameCount();
                JSONObject cachedMDSD = (JSONObject)modelsDatasetFull.getJSONObject("storageDirectory").clone();
                JSONObject cachedMDD = (JSONObject)modelsDatasetFull.getJSONObject("data").clone();
                Thread.sleep(100);
                this.updateProgress(0.2, 1);
                final boolean[] flag = {false};
                Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        //System.out.println(dir);
                        if (dir.getNameCount() == (rootPathCount) && !rootPath.equals(dir))
                            return cachedMDSD.containsValue(dir.getFileName().toString()) ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
                        if (dir.getNameCount() == (rootPathCount + 1) && cachedMDD.containsKey(dir.getFileName().toString())) {
                            if (AssetCtrl.isVerifiedAsset(dir.toFile(), cachedMDD)) {
                                cachedMDD.remove(dir.getFileName().toString());
                                return FileVisitResult.CONTINUE;
                            } else if (!AssetCtrl.isValidAsset(dir.toFile(), cachedMDD)) {
                                return FileVisitResult.CONTINUE;
                            } else {
                                dialogGraphic[0] = IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING);
                                dialogHeader[0] = "已发现问题，模型资源可能不完整。";
                                dialogContent[0] = "位于 " + dir + " 的模型资源，可能" + (AssetCtrl.isIntegralAsset(dir.toFile(), cachedMDD) ? "已被修改" : "缺少关键文件") + "。\n请尝试重新下载模型。";
                                flag[0] = true;
                                return FileVisitResult.TERMINATE;
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
                if (flag[0] || this.isCancelled())
                    return false;
                Thread.sleep(100);
                this.updateProgress(0.7, 1);
                for (String key : cachedMDD.keySet()) {
                    try {
                        if (!cachedMDD.getJSONObject("key").getJSONObject("checksum").isEmpty()) {
                            dialogGraphic[0] = IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING);
                            dialogHeader[0] = "已发现问题，模型资源可能不完整。";
                            dialogContent[0] = "没有找到 " + key + " 的模型资源。\n请尝试重新下载模型。";
                            flag[0] = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
                Thread.sleep(100);
                this.updateProgress(1, 1);
                if (flag[0] || this.isCancelled())
                    return false;
                dialogGraphic[0] = IconUtil.getIcon(IconUtil.ICON_SUCCESS_ALT, IconUtil.COLOR_SUCCESS);
                dialogHeader[0] = "模型资源是完整的。";
                dialogContent[0] = "这只能说明本地的模型资源是完整的，但不一定是最新的。";
                return true;
            }
        };
        task.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Worker.State.SUCCEEDED))
                if (dialogGraphic[0] != null && dialogHeader[0] != null && dialogContent[0] != null)
                    popNotice(dialogGraphic[0], "验证资源完整性", dialogHeader[0], dialogContent[0]).show();
        });
        foregroundTask(task, "验证资源完整性", "正在检验模型资源完整性...", "这可能需要数秒", true);
    }

    private void foregroundCheckUpdate(boolean $popNotice, String $sourceStr) {
        try {
            Files.createDirectories(new File(tempDirPath).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String queryStr = "?type=queryVersion&cliVer=" + ArkHomeFX.appVersionStr + "&source=" + $sourceStr;
        Task<Boolean> task = createDownloadTask(urlApi + queryStr, tempQueryVersionCachePath);
        Dialog<ButtonType> dialog = foregroundTask(task, "正在检查软件更新", "正在下载软件版本信息...", "正在尝试建立连接", true);
        task.setOnSucceeded(e -> {
            dialog.close();
            try {
                JSONObject queryVersionResult = Objects.requireNonNull(JSONObject.parseObject(FileUtil.readByte(new File(tempQueryVersionCachePath))));
                // TODO show in-test version
                if (queryVersionResult.getString("msg").equals("success")) {
                    int[] stableVersionResult = queryVersionResult.getJSONObject("data").getObject("stableVersion", int[].class);
                    if (stableVersionResult[0] > ArkHomeFX.appVersion[0] ||
                            (stableVersionResult[0] == ArkHomeFX.appVersion[0] && stableVersionResult[1] > ArkHomeFX.appVersion[1]) ||
                            (stableVersionResult[0] == ArkHomeFX.appVersion[0] && stableVersionResult[1] == ArkHomeFX.appVersion[1] && stableVersionResult[2] > ArkHomeFX.appVersion[2])) {
                        popNotice(IconUtil.getIcon(IconUtil.ICON_INFO_ALT, IconUtil.COLOR_INFO), "检查软件更新", "恭喜，检测到软件有新的版本。",
                                ArkHomeFX.appVersionStr + " -> " + stableVersionResult[0] + "." + stableVersionResult[1] + "." +stableVersionResult[2] + "\n请访问官网或GitHub下载新的版本。").show();
                    } else {
                        popNotice(IconUtil.getIcon(IconUtil.ICON_SUCCESS_ALT, IconUtil.COLOR_SUCCESS), "检查软件更新", "尚未发现新的稳定版本。",
                                "当前版本：" + ArkHomeFX.appVersionStr).show();
                    }
                } else {
                    if ($popNotice)
                        popNotice(IconUtil.getIcon(IconUtil.ICON_DANGER_ALT, IconUtil.COLOR_DANGER), "检查软件更新", "服务器返回了无效的消息。",
                                "可能是兼容性问题或服务器不可用。\n您可以访问官网或GitHub，手动查看是否有新版本。").show();
                }
            } catch (IOException ex) {
                if ($popNotice)
                    popError(ex).show();
            }
        });
    }

    public Task<Boolean> createDownloadTask(String $remotePath, String $localPath) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                System.out.println("[MultiThreading] Start downloading");
                URL urlFile;
                HttpsURLConnection connection = null;
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                File file = new File($localPath);
                try {
                    urlFile = new URL($remotePath);
                    connection = NetUtil.createHttpsConnection(urlFile, httpTimeout, httpTimeout, httpsTrustAll);
                    bis = new BufferedInputStream(connection.getInputStream(), bufferSize);
                    bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()), bufferSize);
                } catch (IOException e) {
                    try {
                        if (connection != null && connection.getInputStream() != null)
                            connection.getInputStream().close();
                        if (bis != null)
                            bis.close();
                        if (bos != null)
                            bos.close();
                    } catch (Exception ignored){
                    }
                    //e.printStackTrace();
                    throw e;
                }
                int len = bufferSize;
                int unit_KB = 1024;
                int unit_MB = unit_KB * 1024;
                long sum = 0;
                long max = connection.getContentLengthLong();
                byte[] bytes = new byte[len];
                try {
                    while ((len = bis.read(bytes)) != -1) {
                        bos.write(bytes, 0, len);
                        sum += len;
                        this.updateMessage("当前已下载：" + (sum / unit_KB) + " KB");
                        this.updateProgress(sum, max);
                        if (this.isCancelled()) {
                            this.updateMessage("下载进程已被取消");
                            break;
                        }
                    }
                    this.updateProgress(max, max);
                    bos.flush();
                } catch (IOException e) {
                    //e.printStackTrace();
                    throw e;
                } finally {
                    try {
                        if (connection != null && connection.getInputStream() != null)
                            connection.getInputStream().close();
                        if (bis != null)
                            bis.close();
                        if (bos != null)
                            bos.close();
                    } catch (Exception ignored){
                    }
                }
                System.out.println("[MultiThreading] Download completed, total size:"+sum);
                return this.isDone() && !this.isCancelled();
            }
        };
    }

    public Task<Boolean> createUnzipTask(String $zipPath, String $destPath) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                System.out.println("[MultiThreading] Start unzipping");
                try {
                    ZipUtil.unzip($zipPath, $destPath, true);
                } catch (IOException e) {
                    //e.printStackTrace();
                    throw e;
                }
                System.out.println("[MultiThreading] Unzip done");
                return this.isDone() && !this.isCancelled();
            }
        };
    }

    public Task<Boolean> createModelsMovingTask(String $rootPath, String $modelsDataPath) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if (!new File($rootPath).isDirectory())
                    throw new FileNotFoundException("The directory " + $rootPath + " not found.");
                Path rootPath = new File($rootPath).toPath();
                int rootPathCount = rootPath.getNameCount();
                final boolean[] hasDataset = {false};
                Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (file.getNameCount() == (rootPathCount + 2) && file.getName(rootPathCount + 1).toString().equals($modelsDataPath)) {
                            Files.move(file, file.getFileName(), StandardCopyOption.REPLACE_EXISTING);
                            hasDataset[0] = true;
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (dir.getNameCount() == (rootPathCount + 2)) {
                            if (Files.exists(dir.getFileName()))
                                FileUtil.delete(dir.getFileName(), false);
                            Files.move(dir, dir.getFileName(), StandardCopyOption.REPLACE_EXISTING);
                            //System.out.println("Moved:" + dir.getFileName());
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
                if (!hasDataset[0])
                    throw new FileNotFoundException("The file " + $modelsDataPath + " not found.");
                return true;
            }
        };
    }

    private void dealModelSearch(String $keyWords) {
        System.out.println("Input: " + $keyWords);
        searchModelInput.setText($keyWords);
        searchModelList.getChildren().clear();
        AssetCtrl[] result = AssetCtrl.searchByKeyWords($keyWords, foundModelAssets);
        String[] assetIdList = AssetCtrl.getAssetIdList(result);
        for (Pane item : foundModelItems) {
            for (String assetId : assetIdList) {
                if (item.getId().equals(assetId)) {
                    searchModelList.getChildren().add(item);
                    break;
                }
            }
        }
    }

    private void dealModelRandom() {
        if (!assertModelLoaded(true))
            return;
        int idx = (int)(Math.random() * (foundModelItems.length - 1));
        Pane item = foundModelItems[idx];
        for (AssetCtrl assetCtrl : foundModelAssets) {
            if (item.getId().equals(assetCtrl.assetId)) {
                searchModelList.getChildren().clear();
                searchModelList.getChildren().add(item);
                selectModel(assetCtrl, item);
                break;
            }
        }
    }

    private void dealModelReload() {
        initModelAssets(true);
        loadFailureTip.setVisible(foundModelItems.length == 0);
        startBtn.setDisable(foundModelItems.length == 0);
        dealModelSearch("");
        System.gc();
    }

    private Pane getMenuItem(AssetCtrl $assetCtrl, VBox $container) {
        final double width = $container.getPrefWidth() - $container.getPadding().getLeft() - $container.getPadding().getRight();
        final double height = 30;
        final double divide = 0.618;
        Pane item = new Pane();
        item.getStyleClass().add("Search-models-item");
        item.setMinSize(width, height);
        Label name = new Label($assetCtrl.toString());
        name.getStyleClass().addAll("Search-models-label", "Search-models-label-primary");
        name.setPrefSize(width * divide, height);
        name.setLayoutX(0);
        Label alias1 = new Label($assetCtrl.skinGroupName);
        alias1.getStyleClass().addAll("Search-models-label", "Search-models-label-secondary");
        alias1.setPrefSize(width * (1 - divide), height);
        alias1.setLayoutX(width * divide);
        item.getChildren().addAll(name, alias1);
        item.setId($assetCtrl.assetId);
        item.setOnMousePressed(e -> {
            selectModel($assetCtrl, item);
        });
        item.setOnKeyTyped(e -> {
            if (e.getCode().getName().equals(KeyCode.ENTER.getName()))
                selectModel($assetCtrl, item);
        });
        return item;
    }

    private void selectModel(AssetCtrl $asset, Pane $item) {
        if (selectedModelItem != null)
            selectedModelItem.getStyleClass().setAll("Search-models-item");
        if ($item != null)
            $item.getStyleClass().add("Search-models-item-active");
        selectedModelAsset = $asset;
        selectedModelItem = $item;
        // Display details
        final String tooltipStyle = "-fx-font-size:10px;-fx-font-weight:normal;";
        selectedModelName.setText($asset.name);
        selectedModelAppellation.setText($asset.appellation);
        selectedModelSkinGroupName.setText($asset.skinGroupName);
        selectedModelType.setText($asset.type);
        Tooltip selectedModelNameTip = new Tooltip("文件编号：" + $asset.assetId);
        Tooltip selectedModelAppellationTip = new Tooltip("角色代号：" + $asset.appellation);
        Tooltip selectedModelSkinGroupNameTip = new Tooltip("皮肤识别码：" + $asset.skinGroupId);
        Tooltip selectedModelTypeTip = new Tooltip("角色类型：" + $asset.type);
        selectedModelNameTip.setStyle(tooltipStyle);
        selectedModelAppellationTip.setStyle(tooltipStyle);
        selectedModelSkinGroupNameTip.setStyle(tooltipStyle);
        selectedModelTypeTip.setStyle(tooltipStyle);
        selectedModelName.setTooltip(selectedModelNameTip);
        selectedModelAppellation.setTooltip(selectedModelAppellationTip);
        selectedModelSkinGroupName.setTooltip(selectedModelSkinGroupNameTip);
        selectedModelType.setTooltip(selectedModelTypeTip);
        // Apply to config
        config.character_recent = $asset.getAssetFilePath("");
        config.saveConfig();
    }

    private boolean assertModelLoaded(boolean $doPopNotice) {
        if (modelsDatasetFull == null) {
            // Not loaded:
            if ($doPopNotice)
                popNotice(IconUtil.getIcon(IconUtil.ICON_WARNING_ALT, IconUtil.COLOR_WARNING), "未能加载模型", "请确保模型加载成功后再进行此操作。",
                        "请先在[选项]中进行模型下载。\n如您已下载模型，请尝试点击[重载]按钮。").show();
            return false;
        } else {
            // Loaded:
            return true;
        }
    }
}