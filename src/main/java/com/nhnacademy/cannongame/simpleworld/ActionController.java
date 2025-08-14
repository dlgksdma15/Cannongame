package com.nhnacademy.cannongame.simpleworld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionController {
    // 필드
    private Collidable selectedObject;
    private Timeline actionChangeTimer;
    private Map<Object, Integer> collisionCounts;
    private List<Object> gameObjects; // World의 객체 리스트

    public ActionController(List<Object> gameObjects) {
        this.gameObjects = gameObjects;
        this.collisionCounts = new HashMap<>();
        setupEventListeners();
        setupActionChangeTimer();
    }

    // 객체 선택 및 키보드 이벤트 핸들러 설정
    private void setupEventListeners() {
        // 키보드 이벤트 핸들러 (메인 Scene에 등록)
        // 예시: scene.setOnKeyPressed(this::handleKeyPress);

        // 마우스 클릭 이벤트 핸들러 (메인 Scene에 등록)
        // 예시: scene.setOnMouseClicked(this::handleMouseClick);
    }

    // 마우스 클릭 이벤트 처리 메소드
    public void handleMouseClick(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // 이전에 선택된 객체 하이라이트 해제
        if (selectedObject != null) {
            if (selectedObject instanceof Ball) {
                ((Ball) selectedObject).setColor(Color.BLUE); // 원래 색상으로 되돌리기
            } else if (selectedObject instanceof Box) {
                ((Box) selectedObject).setColor(Color.RED);
            }
        }

        // 클릭된 위치에 있는 객체 선택
        selectedObject = null;
        for (Object obj : gameObjects) {
            if (obj instanceof Collidable) {
                Collidable collidableObj = (Collidable) obj;
                if (collidableObj.getBounds().contains(mouseX, mouseY)) {
                    selectedObject = collidableObj;
                    break;
                }
            }
        }

        // 새로운 객체 하이라이트
        if (selectedObject != null) {
            if (selectedObject instanceof Ball) {
                ((Ball) selectedObject).setColor(Color.YELLOW);
            } else if (selectedObject instanceof Box) {
                ((Box) selectedObject).setColor(Color.YELLOW);
            }
        }
    }

    // 키보드 입력 이벤트 처리 메소드
    public void handleKeyPress(KeyEvent event) {
        if (selectedObject == null) return;

        CollisionAction newAction = selectedObject.getCollisionAction();
        if (event.getCode() == KeyCode.B) {
            newAction = CollisionAction.BOUNCE;
        } else if (event.getCode() == KeyCode.D) {
            newAction = CollisionAction.DESTROY;
        } else if (event.getCode() == KeyCode.S) {
            newAction = CollisionAction.STOP;
        } else if (event.getCode() == KeyCode.P) {
            newAction = CollisionAction.PASS;
        } else if (event.getCode() == KeyCode.C) {
            newAction = CollisionAction.CUSTOM;
        }

        if (selectedObject instanceof Box) {
            ((Box) selectedObject).setCollisionAction(newAction);
        } else if (selectedObject instanceof Ball) {
            ((Ball) selectedObject).setCollisionAction(newAction);
        }
    }

    // 10초마다 자동 액션 변경 타이머 설정
    private void setupActionChangeTimer() {
        actionChangeTimer = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    for (Object obj : gameObjects) {
                        if (obj instanceof Collidable) {
                            Collidable collidable = (Collidable) obj;
                            CollisionAction currentAction = collidable.getCollisionAction();
                            CollisionAction nextAction = null;

                            // BOUNCE -> STOP -> PASS -> BOUNCE 순환
                            if (currentAction == CollisionAction.BOUNCE) {
                                nextAction = CollisionAction.STOP;
                            } else if (currentAction == CollisionAction.STOP) {
                                nextAction = CollisionAction.PASS;
                            } else if (currentAction == CollisionAction.PASS) {
                                nextAction = CollisionAction.BOUNCE;
                            }

                            if (nextAction != null) {
                                if (collidable instanceof Box) {
                                    ((Box) collidable).setCollisionAction(nextAction);
                                } else if (collidable instanceof Ball) {
                                    ((Ball) collidable).setCollisionAction(nextAction);
                                }
                            }
                        }
                    }
                })
        );
        actionChangeTimer.setCycleCount(Timeline.INDEFINITE);
        actionChangeTimer.play();
    }

    // 충돌 횟수 업데이트
    public void incrementCollisionCount(Collidable obj) {
        int count = collisionCounts.getOrDefault(obj, 0) + 1;
        collisionCounts.put(obj, count);

        // 충돌 횟수 기반 액션 변경
        if (count == 5 && obj.getCollisionAction() == CollisionAction.BOUNCE) {
            if (obj instanceof Box) {
                ((Box) obj).setCollisionAction(CollisionAction.STOP);
            } else if (obj instanceof Ball) {
                ((Ball) obj).setCollisionAction(CollisionAction.STOP);
            }
        } else if (count == 10 && obj.getCollisionAction() == CollisionAction.STOP) {
            if (obj instanceof Box) {
                ((Box) obj).setCollisionAction(CollisionAction.DESTROY);
            } else if (obj instanceof Ball) {
                ((Ball) obj).setCollisionAction(CollisionAction.DESTROY);
            }
        } else if (count == 15) {
            System.out.println("새로운 기능 해금!");
            // 여기에 새로운 기능 해금 로직 추가
        }
    }
}