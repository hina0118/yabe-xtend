yabe-xtend
==========

現状ではModelをXtendで書き換えたのみの状態です

TODO
----------

- コントローラをXtendで書き換える
- yabeの動作確認

問題点
----------

- CRUD、Secureモジュールのクラス（app配下のソースコード）を参照できない
- Fixtures.loadがうまく動作しない（Xtendで生成されたクラスのフィールドが_（アンダーバー）ではじまる名称のため？）
