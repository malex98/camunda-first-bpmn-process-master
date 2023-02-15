window.camTasklistConf = {
  // change the app name and vendor
  // app: {
  //   name: 'Todos',
  //   vendor: 'Company'
  // },
  //
  // configure the date format
  // "dateFormat": {
  //   "normal": "LLL",
  //   "long":   "LLLL"
  // },
  //
  "locales": {
      "availableLocales": ["en", "ru"],
      "fallbackLocale": "ru"
    },
  //
  // // custom libraries and scripts loading and initialization,
  // // see: http://docs.camunda.org/guides/user-guide/#tasklist-customizing-custom-scripts
  // customScripts: {
  //   // AngularJS module names
  //   ngDeps: ['ui.bootstrap'],
  //   // RequireJS configuration for a complete configuration documentation see:
  //   // http://requirejs.org/docs/api.html#config
  //   deps: ['jquery', 'custom-ui'],
  //   paths: {
  //     // if you have a folder called `custom-ui` (in the `scripts` folder)
  //     // with a file called `scripts.js` in it and defining the `custom-ui` AMD module
  //     'custom-ui': 'custom-ui/scripts'
  //   }
  // },

  'shortcuts': {
    'claimTask': {
      'key': 'ctrl+alt+c',
      'description': 'утверждает текущую выбранную задачу'
    },
    'focusFilter': {
      'key': 'ctrl+shift+f',
      'description': 'установить фокус на первый фильтр в списке'
    },
    'focusList': {
      'key': 'ctrl+alt+l',
      'description': 'устанавливает фокус на первую задачу в списке'
    },
    'focusForm': {
      'key': 'ctrl+alt+f',
      'description': 'устанавливает фокус на первое поле ввода в форме задачи'
    },
    'startProcess': {
      'key': 'ctrl+alt+p',
      'description': 'открывает модальное диалоговое окно запуска процесса'
    }
  }
};
